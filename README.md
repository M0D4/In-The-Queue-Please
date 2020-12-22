# In-The-Queue-Please!

## In Brief, About Queuing Theory:

Queuing theory (or queueing theory) refers to the mathematical study of the formation, function, and congestion of waiting lines, or queues. The study of queues comes under a discipline of Operations Research called Queueing Theory and is a primary methodological framework for evaluating resource performance besides simulation. Queueing theory is generally considered a branch of operations research because the results are often used when making business decisions about the resources needed to provide a service.

At its core, a queuing situation involves two parts:
* Someone or something that requests a service—usually referred to as the customer, job, or request.
* Someone or something that completes or delivers the services—usually referred to as the server.

> Queueing theory is used in software development for purposes such as project management kanban boards, inter-process communication message queues, and devops continuous deployment pipelines. A queueing model is constructed so that queue lengths and waiting time can be predicted.

## Queueing theory notations:

Queueing theory uses notation with Greek letters. We use some of the popular notation; we also add some custom notion that help us with software projects.

### Kendall Notation:

David G. Kendall devised a shorthand notation to describe a queueing system containing a single waiting queue: (A / B / X / Y / Z).

* A : Customer arriving pattern (Inter-arrival-time distribution).
* B : Service pattern (Service-time distribution).
* X : Number of parallel servers.
* Y : System capacity.
* Z : Queueing discipline.

Examples: 
* M / M / 1 / ∞ / FCFS 
* M / M / 1 / K

### Inter-arrival rate, service rate, dropout rate:

The most important notation:
  * λ: inter-arrival rate. This measures how fast new customers are coming into the queue.
  * μ: service rate. This measures how fast customers in the queue are being handled.
  * σ: dropout rate. This measures how fast customers are skipping out the queue unhandled.

Examples:
  * λ = μ means the arrival rate equals the service rate; the queue is staying the same size, other than dropouts.
  * λ > μ means the arrival rate is greater than the service rate; the queue is getting larger, other than dropouts.
  * λ < μ means the arrival rate is less than the service rate; the queue is getting smaller, other than dropouts.

### Utilization ratio:

The most important notation that summarizes a queue:
  * ρ: utilization ratio = λ / μ

Examples:
  * ρ = 1 means the arrival rate is equal to the service rate; the queue is staying the same size.
  * ρ > 1 means the arrival rate is greater than the service rate; the queue is getting larger.
  * ρ < 1 means the arrival rate is less than the service rate; the queue is getting smaller.

### Count:

We count customers often, and we use this notation:
  * κ: count (capacity of the system).

Example:
  * κ = 100 means there are 100 customers.
  * κ > 100 means there are more than 100 customers.
  * κ ≫ 100 means there are many more than 100 customers.

### Standard notations:

Standard notations for queueing theory also uses these symbols:
  * n: the customer number in the system.
  * C: the number of parallel servers.
  * M: the initial number of customers.

## Our Project:

The understanding and prediction of the stochastic behavior of these queues will provide a theoretical insight into the dynamics of these shared resources and how they can be designed to provide better utilization. And the modeling and analysis of waiting queues/networks is the main implemented subject in this project.

We implemented, using Java and JavaFX GUI, the Deterministic and Stochastic Models:

* Model D/D/1/K-1
* Model M/M/1
* Model M/M/1/K
* Model M/M/C
* Model M/M/C/K

Including graph result (if exists for a model), ending up with a cool CSS style!

Based on the model you choose and the inputs you have, the project can asnwer you the following questions:

* Sketching number of the customers at time (t), (Only for Model D/D/1/K-1).
* Expected number of the customers in the system (L).
* Expected number of the customers in the queue (Lq).
* Expected waiting time spent in the system (W).
* Expected waiting time spent in the queue (Wq).

## Screenshots:

<div align="center">

![Screenshot from 2020-12-22 15-29-14](https://user-images.githubusercontent.com/52632898/102894846-65341180-446c-11eb-8549-4be116aff37b.png)

<hr />

![Screenshot from 2020-12-22 15-31-00](https://user-images.githubusercontent.com/52632898/102894972-914f9280-446c-11eb-967c-6f6421fb8cd4.png)

<hr />

![Screenshot from 2020-12-22 15-33-45](https://user-images.githubusercontent.com/52632898/102894936-83017680-446c-11eb-8b93-970ac2b533a0.png)

</div>


## Contributors:

> This project exists thanks to all wonderful people who contribute: 

[![](https://avatars1.githubusercontent.com/u/51851957?s=80&v=4)](https://github.com/M0D4)
[![](https://avatars0.githubusercontent.com/u/55098152?s=80&v=4)](https://github.com/khaled-diab)
[![](https://avatars2.githubusercontent.com/u/52632898?s=80&v=4)](https://github.com/AHR9N)
