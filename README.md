# Agoda Coding Challenge

We would like to have a restful getWeeklySummary API for third party bug tracking system. 
We have an existing third party getIssues API, given a project id, it returns response
containing issues and other metadata about that project.

We should design the internal API that should be very fast (<50ms milliseconds), near real-time data
and respect the fact that 3rd party bug tracking system has rate limiting in place 
(max. /getIssues calls 1 request per minute).

## Getting Started

### Git clone:

Desktop > git clone https://github.com/mkarthick19/Agoda.git

### Running the application

cd Agoda/

Agoda > mvn spring-boot:run

### Get weekly summary API
cd scripts/

Agoda/scripts > sh getWeeklySummary_project1.sh
 
Agoda/scripts > sh getWeeklySummary_project2.sh 

Agoda/scripts > sh getWeeklySummary_project3.sh 

Agoda/scripts > sh getWeeklySummary_badRequest.sh 

### Running the tests
Agoda/> mvn test

## Available Endpoint

GET /getWeeklySummary  (Sample Input Request/Response):

project1.json: 

Sample Request:
{
"project_id" : "project1",
"from_week" : "2019W01",
"to_week" : "2019W03",
"types" : ["bug"],
"states" : ["open"]
}


Agoda/scripts > sh getWeeklySummary_project1.sh 


 {
   
  "project_id" : "project1",

   "weekly_summaries" : [

      {

         "week" : "2019W01",

         "state_summaries" : [

            {
               "state" : "open",
               "count" : 1,
               "issues" : [
                  {
                     "issue_id" : "issue1",
                     "type" : "bug"
                  }
               ]

            }
         ]
      },
      {
         "week" : "2019W02",
         "state_summaries" : [
            {
               "state" : "open",
               "count" : 1,
               "issues" : [
                  {
                     "issue_id" : "issue2",
                     "type" : "bug"
                  }
               ]
            }
         ]
      },
      {
         "week" : "2019W03",
         "state_summaries" : []
      },
      {
         "week" : "2019W04",
         "state_summaries" : [
            {
               "state" : "open",
               "count" : 2,
               "issues" : [
                  {
                     "issue_id" : "issue1",
                     "type" : "bug"
                  },
                  {
                     "issue_id" : "issue2",
                     "type" : "bug"
                  }
               ]
            }
         ]
      }
   ]
 }

## Design Explained:

Our Bug tracking system has 3 main things:

1. Populate local data structures for input requests.
2. Sync Service
3. Get Weekly Summary

### Input Request:

Each input request is validated and once the validation is successful, we maintain 2 maps, 
New Project Map and Old Project Map.

New Project Map: Each input project is initially put into new project map with frequency 
set to 1 and incremented for subsequent requests of that particular project.

Old Project Map: This map holds already synched projects and its frequency similar to New
Project Map.


### Sync Service: 

Sync service is responsible for communicating with third party getIssues API every minute 
(according to properties in application.yml, default is 60000 ms = 1 min) and updates the local data store.

The key idea in our implementation is, given that we can invoke getIssues API only once per minute,
How do we choose project and sync the data in the local data store?

The idea is, we choose the maximum frequency project from the New Project Map. Once we sync it,
we remove that project from New Project Map and put it into Old Project Map resetting its frequency.

In case if the New Project Map is empty, which means we have already synched all the projects at least once.
We then again choose the maximum frequency project from Old Projects Map and sync it. Once it is completed,
we will reset its frequency in the Old Project map.

Once the project is chosen, sync service communicates with third Party getIssues API and updates the 
response (can be new/diff of existing) in the local data store.

### Get Weekly Summary:

For the input request, it fetches the available response from the local data store and returns it.


### Concurrency Handling Explained

Concurrency Hash Map is used. In our system, we have multiple readers (getWeeklySummary) and 1 writer (Sync service).
It allows concurrent access to the map, where part of the map is only getting locked while adding or updating the map.
So ConcurrentHashMap allows concurrent threads to read the value without locking at all, to improve the performance.


## Time Complexity

GET /getWeeklySummary:

For a particular week, it takes O(1) time to fetch the issues from the local data store. 
Overall, it takes O(weekDiff), where weekDiff=ToWeek-FromWeek+1, to return the entire response. 


