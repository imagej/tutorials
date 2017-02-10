package org.example

import net.imagej.ImageJ

ij = new ImageJ()

// load an image of a colorful primate
path = "http://wsr.imagej.net/images/baboon.jpg"
ape = ij.scifio().datasetIO().open(path)

// Take a look at our ape!
ij.ui().show(ape)
