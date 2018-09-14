# TinderJobs

Android app to connect employees and employers using a Tinder-like swipe mechanism with a [Express 4/Node JS backend](https://github.com/gopa2000/TinderJobsBE)

The app uses the name "Jobstr" since I wasn't allowed to use TinderJobs as a name for a course project ¯\_(ツ)_/¯.

## Process Flow
The app model includes accounts for an Employer and Job Seeker. An employer can put a multiple open positions as listing on a single account while a job-seeker has a one-one association with an account.

The backend maintains a "Like-Table" that keeps a track of all the swipes and does a simple table look-up at regular intervals to inform the user of a match. The match opens a socket connection to a mutual chatroom on the backend server to which both clients post messages to.

## Screenshots

| Login Screen |
|--------------|
|<img src="https://user-images.githubusercontent.com/6462536/45531825-8999e180-b7bf-11e8-8ece-99849a955a1e.jpg" width="250">|

More coming soon..
