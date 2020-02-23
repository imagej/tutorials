ImageJ maven-projects > howto migration notes
===

<center>

### **Testing maven-project migrated files (howto):**

| File name | Moved from (maven-projects) | Moved to (howto) | Runs (Y/N) | Issues | Action taken | Notes |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| `AddROIs.java` | add-rois | images | **Y** | --- | --- | Runs in IJ2. |
| `AddTwoDatasets.java` | add-two-datasets | datasets | **Y** | --- | --- | Runs in IJ1. |
| `DisplayATable.java` | call-modern-from-legacy | adv | **Y** | --- | --- | Rename class to be more descriptive |
| `CreateANewOp.java` | create-a-new-op | ops | **Y** | --- | --- | --- |
| `Ramp.java` | create-a-new-op | ops > images | **Y** | --- | Renamed file to `RampOp.java`. | Runs in IJ1. |
| `RandomBlobs.java` | create-a-new-op | ops > images | **Y** | --- | Renamed file to `RandomBlobsOp.java` | Runs in IJ1. |
| `CommandWithPreview.java` |commands-with-preview | ui > preview | **Y** | --- | --- | Runs in IJ1. |
| `PreviewCheckbox.java` | commands-with-preview | ui > preview | **Y** | --- | --- | Runs in IJ1. |
| `ExecuteCommands.java` | execute-commands | commands | **Y** | Broken import of `org.scijava.plugins.commands.io.OpenFile` | --- | Idea: fix by requesting IO from scijava context. |
| `DatasetWrapping.java` | ij2-image-plus | adv | **N** | Doesn't seem to load anything into the imagej frame. | --- | Runs in IJ1. |
| `IntroToImageJAPI.java` | intro-to-imagej-api | adv | **Y** | --- | --- | --- |
| `ListenToEvents.java` | listen-to-events | events | **Y** | Does not output events to terminal. | Request IJ2 interface by calling `ij.ui().showUI("swing")`. | Works when running in IJ2, not in IJ1 |
| `LoadAndDisplayDataset.java` | load-and-display-dataset | datasets | **Y** | --- | --- | Runs in IJ1. |
| `LowPassFilter.java` | low-pass-filter | images > filter | **Y** | Output image is different when when running from IJ1 and IJ2. | --- | Runs in IJ1 |
| `GetMetadata.java` | metadata | metadata | **Y** | Displayed sample image is malformed. | --- | Runs in IJ1. |
| `CopyLabels.java` | mixed-world-command | commands | **Y** | --- | --- | Runs in IJ1. |
| `GradientImage.java` | simple-commands | commands | **Y** | --- | --- | Runs in IJ1. |
| `HelloWorld.java` | simple-commands | commands | **Y** | --- | --- | Runs in IJ1. |
| `OpenImage.java` | simple-commands | commands | **Y** | --- | --- | Runs in IJ1. |
| `OpenScaleSaveImage.java` | simple-commands | commands | **Y** | --- | --- | Runs in IJ1. |
| `DeconvolutionCommand.java` | swing-example | ui | --- | --- | --- | Depedency for `DeconvolutionDialog.java` |
| `DeconvolutionCommandSwing.java` | swing-example | ui | --- | --- | --- | Dependency for `DeconvolutionDialog.java` |
| `DeconvolutionDialog.java` | swing-example | ui | **Y** | OK and Cancel buttons do not work. Crashes after interaction. | --- | **Do not migrate until workout bug issue** |
| `SwingExample.java` | swing-example | ui | **Y** | Called swing ui via `ij.ui().showUI("swing")`. | --- | Runs in IJ1. |
| `TableTutorial.java` | tables | tables | **Y** | --- | --- | --- |
| `ConvolutionOps.java` | using-ops | ops | **Y** | --- | --- | --- |
| `UsingOps.java` | using-ops | ops | **Y** | --- | --- | --- |
| `UsingOpsDog.java` | using-ops |  ops | **Y** | --- | --- | Runs in IJ1. |
| `UsingOpsLabeling.java` | using-ops | ops | **Y** | --- | --- | Runs in IJ1. |
| `UsingSpecialOps.java` | using-ops |ops | **Y** | --- | --- | --- |
| `WidgetDemo.java` | widget-demo | ui | **Y** | --- | --- | Runs in IJ1. |


### **Testing howto java files:**

| File name | Location | Runs (Y/N) | Issues | Action taken | Notes |
| :---: | :---: | :---: | :---: | :---: | :---: |
| `DisposeImageJ.java` | app | --- | No `main`. | --- | --- |
| `GetImageJDirectory.java` | app | **Y** | --- | --- | --- |
| `GetJARsInClassPath.java` | app | **Y** | --- | --- | --- |
| `GetSystemInformation.java` | app | **Y** | Broken import of `org.scijava.plugins.commands.debug.SystemInformation`. | Pass `org.scijava.plugins.commands.debug.SystemInformation` as a string instead of a class to `ij.command().run()`. Removed import call. | --- |
| `GetVersionofMavenArtifact.java` | app | **Y** | --- | --- | --- |
| `DisplayError.java` | displays | **Y** | --- | --- | --- |
| `DisplayInfo.java` | displays | **Y** | --- | --- | --- |
| `DisplayWarning.java` | displays | **Y** | --- | --- | --- |
| `CommandThatChecksImageType.java` | extensions | **Y** | --- | --- | Runs in IJ1. |
| `ExampleCommand.java` | extensions | --- | No `main`. | --- | Dependency for `GetExampleCommandResults.java`. |
| `ExampleDynamicCommand.java` | extensions | --- | No `main`. | --- | Dependency for `ModifyCommand.java`. |
| `GetExampleCommandResults.java` | extensions | **Y** | --- | --- | --- |
| `ListAllCommands.java` | extensions | **Y** | --- | --- | --- |
| `ModifyCommand.java` | extensions | **Y** | --- | --- | Runs in IJ1. |
| `RunExampleCommand.java` | extensions | **Y** | --- | --- | Runs in IJ1. |
| `StartImageJHeadless.java` | headless | **Y** | --- | --- | --- |
| `ConvertImageClasses.java` | images | **Y** | --- | --- | --- |
| `CreateImage.java` | images | **Y** | --- | --- | --- |
| `DuplicateImage.java` | images | **Y** | --- | --- | --- |
| `GetOpenImages.java` | images  | **Y** | Displays malformed color image. | Added `ij.ui().showUI("swing")` to `main`. | Displays image correctly when IJ2 interface enforced. | 
| `OpenAndShowImage.java` | images | **Y** | --- | --- | --- |
| `SaveImage.java` | images | **Y** | --- | --- | Runs in IJ1. |
| `DrawCircle.java` | images > drawing | **Y** | --- | --- | --- |
| `DrawRectangle.java` | images > drawing | **Y** | --- | --- | --- |
| `CountCels.java` | images > processing  | **Y** | --- | --- | --- |
| `ProcessChannelsIndividually.java` | images > processing | **Y** | --- | --- | --- |
| `CallService.java` | services | **Y** | --- | --- | --- |
| `ListAllServices.java` | services | **Y** | --- | --- | --- |
| `CreateTable.java` | tables | **Y** | --- | --- | --- |
| `SaveAndLoadTable.java` | tables | **N** | No suitable viewer for display (null). | --- | --- |
| `AskForFile.java` | userinput | **Y** | --- | --- | --- |
| `AskYesNo,java` | userinput | **Y** | --- | --- | --- |
| `ValidateParamter.java` | userinput | **Y** | --- | --- | Runs in IJ1. |

</center>