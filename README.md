[![](https://travis-ci.org/imagej/tutorials.svg?branch=master)](https://travis-ci.org/imagej/tutorials)

This project contains example code for working with
[ImageJ](https://imagej.net/ImageJ) and [SciJava](https://imagej.net/SciJava).


JUPYTER NOTEBOOKS
-----------------

[![Binder](https://mybinder.org/badge.svg)](https://mybinder.org/v2/gh/imagej/tutorials/master)

The easiest way to get started with the ImageJ and SciJava APIs is via the
[ImageJ Jupyter notebooks](https://imagej.github.io/tutorials),
located in the `notebooks` subfolder of this repository.

Use the "launch binder" badge above to try the Jupyter notebooks on the cloud
using [Binder](https://mybinder.org), with no local installation necessary.

The introductory notebooks use the Groovy kernel from
[BeakerX](https://beakerx.com). Several other JVM-based kernels
are usable as well, including Clojure, Java, Kotlin and Scala.

There are also notebooks using the standard Python kernel plus
the [pyimagej](https://pypi.org/project/pyimagej) package,
enabling use of ImageJ from Python programs.

There is [more than one way to install Jupyter](https://jupyter.org/install),
but here is the procedure we recommend to get started quickly:

1. Install [Miniconda](https://conda.io/miniconda.html).
2. Clone this `imagej/tutorials` repository.
3. Open a console and `cd` to your cloned working copy.
4. `conda env create -f environment.yml` to create a conda environment with the
   dependencies these notebooks need.
5. `conda activate scijava` to activate the environment.
6. `jupyter notebook` to launch Jupyter Notebook in a web browser window.
7. In the browser, click into `notebooks`, then click on the
   `ImageJ-Tutorials-and-Demo.ipynb` notebook to open it.

Learn more about Jupyter Notebook on [its web site](https://jupyter.org).

JAVA PROJECTS
-------------

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/imagej/tutorials)

For the
[type-safety-inclined](https://softwareengineering.stackexchange.com/a/38257),
this repository also contains Maven projects written in Java, located in the
`maven-projects` subfolder of this repository.

Use the "Open in Gitpod" button above to run the (non-GUI) Java projects on the
cloud using [Gitpod](https://gitpod.io), with no local installation necessary.

You can import these projects into your favorite IDE:

  * Eclipse: File > Import > Existing Maven Projects
  * NetBeans: File > Open Project
  * IDEA: File > Open Project... (select pom.xml)

Or build and run from the command line:

    mvn
    cd maven-projects/simple-commands
    mvn -Pexec -Dmain-class=GradientImage


LICENSING
---------

To the extent possible under law, the ImageJ developers have waived
all copyright and related or neighboring rights to this tutorial code.

See the [CC0 1.0 Universal license](https://creativecommons.org/publicdomain/zero/1.0/) for details.


SEE ALSO
--------

* The [ImageJ Tutorials](https://imagej.net/Tutorials) and [Development](https://imagej.net/Development) sections of the ImageJ wiki.
