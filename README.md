Kuhnuri CMS
===========

REST service for a DITA-OT processor queue. The queue persists the state of the queue into a database or keeps it only
in memory depending on configuration.

Queue
-----

The queue contains the following fields for an individual job:

* unique UUID
* DITA-OT transtype
* creation timestamp for when the job was added to the queue
* input file URI
* output directory URI
* status: queue, process, done, or error
* internal unique ID

Process logic
-------------

Queue works as FIFO queue.

Related projects
----------------

* http://python-rq.org/
