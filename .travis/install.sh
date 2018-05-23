#!/bin/sh
CONDA_DIR=$HOME/miniconda
if [ ! -d "$CONDA_DIR" ]; then
  if [ "$TRAVIS_PYTHON_VERSION" = "2.7" ]; then
    wget https://repo.continuum.io/miniconda/Miniconda2-latest-Linux-x86_64.sh -O miniconda.sh
  else
    wget https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh -O miniconda.sh
  fi
  bash miniconda.sh -b -p "$CONDA_DIR"
fi
export PATH="$CONDA_DIR/bin:$PATH"
hash -r
conda config --set always_yes yes --set changeps1 no
conda update -q conda
conda info -a
conda env create -n test-environment python="$TRAVIS_PYTHON_VERSION" -f environment.yml
source activate test-environment
