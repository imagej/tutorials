[![](https://travis-ci.org/imagej/tutorials.svg?branch=master)](https://travis-ci.org/imagej/tutorials)

This project contains example code for working with
[ImageJ2](https://imagej.net/software/imagej2) and [SciJava](https://imagej.net/libs/scijava).


JAVA PROJECTS
-------------

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/imagej/tutorials)

For the
[type-safety-inclined](https://softwareengineering.stackexchange.com/a/38257),
this repository also contains Maven projects written in Java, located in the
`java` subfolder of this repository.

Use the "Open in Gitpod" button above to run the (non-GUI) Java projects on the
cloud using [Gitpod](https://gitpod.io), with no local installation necessary.

You can import these projects into your favorite IDE:

  * Eclipse: File > Import > Existing Maven Projectsnotebooks
  * NetBeans: File > Open Project
  * IDEA: File > Open Project... (select pom.xml)

Or build and run from the command line:

    mvn
    cd java/simple-commands
    mvn -Pexec -Dmain-class=GradientImage


LICENSING
---------

To the extent possible under law, the ImageJ developers have waived
all copyright and related or neighboring rights to this tutorial code.

See the [CC0 1.0 Universal license](https://creativecommons.org/publicdomain/zero/1.0/) for details.


SEE ALSO
--------

* The [Tutorials](https://imagej.net/tutorials) and [Development](https://imagej.net/develop) sections of the ImageJ wiki.
