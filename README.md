[![](https://travis-ci.org/imagej/tutorials.svg?branch=master)](https://travis-ci.org/imagej/tutorials)
[![Binder](https://mybinder.org/badge.svg)](https://mybinder.org/v2/gh/imagej/tutorials/master)

This project contains example code for working with
[ImageJ2](https://imagej.net/ImageJ2) and
[SciJava](https://imagej.net/SciJava).


JUPYTER NOTEBOOKS
-----------------

The easiest way to get started with the ImageJ and SciJava APIs is via the
[ImageJ Jupyter notebooks](https://nbviewer.jupyter.org/github/imagej/tutorials/blob/master/notebooks/ImageJ_Tutorials_and_Demo.ipynb),
located in the `notebooks` subfolder of this repository.

These notebooks use the
[SciJava Jupyter Kernel](https://github.com/hadim/scijava-jupyter-kernel).
SciJava cells support
[all SciJava script languages](http://imagej.net/Scripting#Supported_languages),
including mixing and matching languages within the same notebook.
The notebooks here use [Groovy](http://imagej.net/Groovy_Scripting).


JAVA PROJECTS
-------------

For the
[type-safety-inclined](https://softwareengineering.stackexchange.com/a/38257),
this repository also contains Maven projects written in Java.
You can import these projects into your favorite IDE:

  * Eclipse: File > Import > Existing Maven Projects
  * NetBeans: File > Open Project
  * IDEA: File > Open Project... (select pom.xml)

Or build and run from the command line:

    cd maven-projects
    mvn
    cd simple-commands
    mvn -Pexec -Dmain-class=GradientImage


LICENSING
---------

To the extent possible under law, the ImageJ developers have waived
all copyright and related or neighboring rights to this tutorial code.

See the [CC0 1.0 Universal license](https://creativecommons.org/publicdomain/zero/1.0/) for details.


SEE ALSO
--------

* The [ImageJ Tutorials](https://imagej.net/Tutorials) and [Development](https://imagej.net/Development) sections of the ImageJ wiki.
