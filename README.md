[![](http://jenkins.imagej.net/job/ImageJ-tutorials/lastBuild/badge/icon)](http://jenkins.imagej.net/job/ImageJ-tutorials/)

This project contains example code for working with [ImageJ2][1].


GETTING STARTED
---------------

You can import these projects into your favorite IDE:

  * Eclipse: File > Import > Existing Maven Projects
  * NetBeans: File > Open Project
  * IDEA: File > Open Project... (select pom.xml)

Or build and run from the command line:

    mvn
    cd load-and-display-dataset
    mvn exec:java -Dexec.mainClass=LoadAndDisplayDataset


LICENSING
---------

To the extent possible under law, the ImageJ developers have waived
all copyright and related or neighboring rights to this tutorial code.

See the [CC0 1.0 Universal license][2] for details.


SEE ALSO
--------

The [ImgLib2 Examples][3], an excellent image processing tutorial.


[1]: http://developer.imagej.net/
[2]: http://creativecommons.org/publicdomain/zero/1.0/
[3]: http://fiji.sc/ImgLib2_Examples
