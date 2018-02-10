# firewall_java

## Background
A core component of Illumio’s product is a host-­‐based firewall. As a simplified model, consider a firewall to be a system which is programmed with a set of predetermined security rules. As network traffic enters and leaves the machine, the firewall rules determine whether the traffic should be allowed or blocked.


## Tools, Technologies, Implementation

> I implemented the soultion in Java and JUnit using IntelliJ.

> The solution is simple add all rules to a set and for any incoming packet check if the rule exists in the set.

> The Set of rules is constructed by getting all possible values in IP and port range and generating a string from combination of all inputs to be added in set.
> As a result, using hashset we have faster lookups and inserts. However we have to save all the rules ultimately increasing the storage complexity.

> As a future scope we can use Trie Data structure to store our rules allowing faster lookups without increasing storage.


## Team Preference

From the very first I was inclined to work in platform team as it developes the functionality that functions as a brain of the whole application and I did gain some insight on how it works in my call with Kiran.
Secondly I would love to work with the data team and learn how things work there too.