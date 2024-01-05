The summary of this in class project was to create a bank simulation where customers would come in and wait in line. That person should be stored in a queue until the current person is done. The output would be information on when people arrived and when they left. The given instructions are below


In this assignment you will implement bank simulation problem we discussed in class. The
problem is also discussed in detail on the textbook. You need to make sure that you
understand the logic of the problem and how to solve the problem on paper before you
actually implement it (especially if you missed the class when we discussed the problem). A
PDF file that includes the pages on the textbook describing the problem is also attached for this
assignment. Please read the pages if you do not understand how to solve the problem.
The two data structures are needed for this problem. One data structure is the queue that
simulates the waiting line in the bank. The queue also includes the customer currently being
served which is at the front of the queue. The element type of the queue is an Event object.
Event is a class that you will need to write.
The other data structure you need to implement is the EventList which is a list of events. This list
includes the next arrival and the next departure event. The list is sorted based on the time.
The input is a text file (named “assg8_input.txt”) of arrival and transaction times. Each line of
the file contains the arrival time and transaction time for a customer. The arrival times are
ordered by time. A sample input file is attached for this assignment.
For this assignment, you will need to write three classes, the Event class, the EventList class,
and the Simulation class (with the main method).
The Event class is for a single event. It should include constructors, get methods, compareTo
method, and toString method, as well as methods to check if an event is an arrival event or a
departure event.
The EventList class should include methods that allow to insert, remove, or retrieve an event
from the EventList. It should also include a default constructor and a method to check if the
event list is empty. Make sure that the EventList is sorted based on time when you add a method
to the list. If an arrival event and a departure event have the same time, the arrival event precedes
the departure event.
The Simulation class should include a method to process an arrival event and a method to
process a departure event, in addition to the main method. The process is started by reading the
first arrival event and adding it to the EventList. After that you can repeat the process by
processing each event in the EventList and updating the queue and EventList accordingly as well
as generating the output.
There are two types of events, arrival event and departure event. For example, an arrival event
(‘A’, 1, 5) indicates that this event has arrival time of 1 and transaction time of 5 (minutes). A
2
departure event (‘D’, 6) indicates that this departure event has departure time of 6. The Event
class will need to include variables that are needed for arrival and departure events.
Your program needs to generate the sequence of the events during the process (see the sample
output on the next page). Your program also needs to calculate the average waiting time. To do
that, you can calculate the waiting time for each customer during the process and then calculate
the average waiting time at the end. A customer’s waiting time is the time between the
customer’s arrival and the time the customer starts the transaction. Your program also needs to
generate the sequence of the events during the process
