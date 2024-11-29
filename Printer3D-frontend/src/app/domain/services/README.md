# CONTAINERS FLOW
A ActiveContainer will hold backend data inside a Subscription and has the mechanism to update that contents on an event.

On creation it should start with the first version of that data so any client that connects to it will get that initial data upon subscription.

When the event is received it will fire the update mechanism, get fresh data from the backend and report that data to any subscribers.
