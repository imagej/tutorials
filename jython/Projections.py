# @OpService ops
# @DisplayService display
# @net.imagej.Dataset inputData

from net.imagej.ops import Ops

dimensionToProject=2

# generate the projected dimension
projectedDimensions=[inputData.dimension(x) for x in range(0, inputData.numDimensions()) if x!=dimensionToProject]
print projectedDimensions

# create memory for projections
maxProjection=ops.create().img(projectedDimensions)
sumProjection=ops.create().img(projectedDimensions)

# use op service to get the max op
maxOp = ops.op(Ops.Stats.Max, inputData)
# use op service to get the sum op
sumOp = ops.op(Ops.Stats.Sum, sumProjection.firstElement(), inputData)

# call the project op passing
# maxProjection: img to put projection in
# image: img to project
# op: the op used to generate the projection (in this case "max")
# dimensionToProject: the dimension to project
ops.image().project(maxProjection, inputData, maxOp, dimensionToProject)

# project again this time use sum projection
ops.image().project(sumProjection, inputData, sumOp, dimensionToProject)

# display the results
display.createDisplay("max projection", maxProjection)
display.createDisplay("sum projection", sumProjection)
