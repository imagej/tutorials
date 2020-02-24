ImageJ maven-projects > howto migration notes
===

<center>

### **Testing maven-project migrated files (howto):**

| File name | Moved from (maven-projects) | Moved to (howto) | Runs (Y/N) | Issues | Action taken | Migrated (Y/N) |Notes |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| `AddROIs.java` | add-rois | images | **Y** | ROIs do not display on image until interacted with. | --- | **Y** | Calls swing UI. |
| `AddTwoDatasets.java` | add-two-datasets | --- | **Y** | Module throws exception if images are not extactly the same `java.lang.IllegalArgumentException: No matching 'net.imagej.ops.Ops$Math$Add' op`. | --- | **N** | Calls AWT UI. |
| `DisplayATable.java` | call-modern-from-legacy | adv | **Y** | --- | Renamed class to `ModernFromLegacy` and file name to `ModernFromLegacy.java` | **Y** | --- |
| `CreateANewOp.java` | create-a-new-op | ops | **Y** | --- | --- | **Y** | --- |
| `Ramp.java` | create-a-new-op | ops | **Y** | --- | Renamed file to `RampOp.java`. | **Y** | Calls AWT UI. |
| `RandomBlobs.java` | create-a-new-op | ops | **Y** | --- | Renamed file to `RandomBlobsOp.java` | **Y** |Calls AWT UI. |
| `CommandWithPreview.java` |commands-with-preview | ui > preview | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `PreviewCheckbox.java` | commands-with-preview | ui > preview | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `ExecuteCommands.java` | execute-commands | commands | **Y** | Broken import of `org.scijava.plugins.commands.io.OpenFile` | --- |  |
| `DatasetWrapping.java` | ij2-image-plus | adv | **N** | Doesn't seem to load anything into the imagej frame. | --- | Runs in IJ1. |
| `IntroToImageJAPI.java` | intro-to-imagej-api | adv | **Y** | --- | --- | **Y** | --- |
| `ListenToEvents.java` | listen-to-events | events | **Y** | AWT image window does not output events to the terminal, swing image window does. | Possible action: Request IJ2 interface by calling `ij.ui().showUI("swing")`. | **N** | Calls AWT UI (image window only). |
| `LoadAndDisplayDataset.java` | load-and-display-dataset | --- | **Y** | Input image drawn incorrectly (legacy bug) | --- | **Y** | Calls AWT UI (image window only). |
| `LowPassFilter.java` | low-pass-filter | images > filter | **Y** | Input image drawn incorrectly (legacy bug) | --- | **Y** |Calls AWT UI. |
| `GetMetadata.java` | metadata | metadata | **Y** | Input image drawn incorrectly (legacy bug) | --- | **Y** | Calls AWT UI. |
| `CopyLabels.java` | mixed-world-command | commands | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `GradientImage.java` | simple-commands | commands | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `HelloWorld.java` | simple-commands | commands | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `OpenImage.java` | simple-commands | commands | **Y** | Input image drawn incorretly (legacy bug) | --- | **Y** | Calls AWT UI. |
| `OpenScaleSaveImage.java` | simple-commands | commands | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `DeconvolutionCommand.java` | swing-example | ui | --- | --- | --- | **N** | Depedency for `DeconvolutionDialog.java` |
| `DeconvolutionCommandSwing.java` | swing-example | ui | --- | --- | --- | **N** |Dependency for `DeconvolutionDialog.java` |
| `DeconvolutionDialog.java` | swing-example | ui | **Y** | OK and Cancel buttons do not work. Crashes after interaction. | --- | **N** |**Do not migrate until workout bug issue** |
| `SwingExample.java` | swing-example | ui | **Y** | Called swing ui via `ij.ui().showUI("swing")`. | --- | **Y** | Calls swing UI. |
| `TableTutorial.java` | tables | tables | **Y** | --- | --- | **Y** | Calls AWT UI |
| `ConvolutionOps.java` | using-ops | ops | **Y** | --- | --- | **Y** | --- |
| `UsingOps.java` | using-ops | ops | **Y** | --- | --- | **Y** | --- |
| `UsingOpsDog.java` | using-ops |  ops | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** | Calls AWT UI. |
| `UsingOpsLabeling.java` | using-ops | ops | **Y** | Input image drawn incorrectly (legacy bug) | --- | **Y** | Calls AWT UI. |
| `UsingSpecialOps.java` | using-ops |ops | **Y** | --- | --- | **Y** | Has commented out code block. Remove? |
| `WidgetDemo.java` | widget-demo | ui | **Y** | --- | --- | **Y** | Calls AWT UI. |


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