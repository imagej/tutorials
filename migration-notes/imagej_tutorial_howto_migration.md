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
| `ExecuteCommands.java` | execute-commands | commands | **Y** | Broken import of `org.scijava.plugins.commands.io.OpenFile` | --- | **N** | --- |
| `DatasetWrapping.java` | ij2-image-plus | datasets | **Y** | Doesn't seem to do anything, opens blank image. | --- | **N** | --- |
| `IntroToImageJAPI.java` | intro-to-imagej-api | adv | **Y** | --- | --- | **Y** | --- |
| `ListenToEvents.java` | listen-to-events | events | **Y** | AWT image window does not output events to the terminal, swing image window does. | Possible action: Request swing UI by calling `ij.ui().showUI("swing")`. | **N** | Calls AWT UI (image window only). |
| `LoadAndDisplayDataset.java` | load-and-display-dataset | --- | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** | Calls AWT UI (image window only). |
| `LowPassFilter.java` | low-pass-filter | images > filter | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** |Calls AWT UI. |
| `GetMetadata.java` | metadata | metadata | **Y** | Input image drawn incorrectly (legacy bug) | --- | **Y** | Calls AWT UI. |
| `CopyLabels.java` | mixed-world-command | commands | **Y** | --- | --- | **Y** | Calls AWT UI. |.
| `GradientImage.java` | simple-commands | commands | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `HelloWorld.java` | simple-commands | commands | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `OpenImage.java` | simple-commands | commands | **Y** | Input image drawn incorretly (legacy bug). | --- | **Y** | Calls AWT UI. |
| `OpenScaleSaveImage.java` | simple-commands | commands | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `DeconvolutionCommand.java` | swing-example | ui | --- | --- | --- | **N** | Depedency for `DeconvolutionDialog.java`. |
| `DeconvolutionCommandSwing.java` | swing-example | ui | --- | --- | --- | **N** |Dependency for `DeconvolutionDialog.java`. |
| `DeconvolutionDialog.java` | swing-example | ui | **Y** | OK and Cancel buttons do not work. Crashes after interaction. | --- | **N** |**Do not migrate until workout bug issue** |
| `SwingExample.java` | swing-example | ui | **Y** | Called swing ui via `ij.ui().showUI("swing")`. | --- | **Y** | Calls swing UI. |
| `TableTutorial.java` | tables | tables | **Y** | --- | --- | **Y** | Calls AWT UI |
| `ConvolutionOps.java` | using-ops | ops | **Y** | --- | --- | **Y** | --- |
| `UsingOps.java` | using-ops | ops | **Y** | --- | --- | **Y** | --- |
| `UsingOpsDog.java` | using-ops |  ops | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** | Calls AWT UI. |
| `UsingOpsLabeling.java` | using-ops | ops | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** | Calls AWT UI. |
| `UsingSpecialOps.java` | using-ops |ops | **Y** | --- | --- | **Y** | Has commented out code block. Remove? |
| `WidgetDemo.java` | widget-demo | ui | **Y** | --- | --- | **Y** | Calls AWT UI. |


### **Testing howto java files:**

| File name | Location | Runs (Y/N) | Issues | Action taken  | Notes |
| :---: | :---: | :---: | :---: | :---: | :---: |
| `DisposeImageJ.java` | app | --- | No `main`. | --- | --- |
| `GetImageJDirectory.java` | app | --- | --- | --- | --- |
| `GetJARsInClassPath.java` | app | ---| --- | --- | --- |
| `GetSystemInformation.java` | app | --- | Broken import of `org.scijava.plugins.commands.debug.SystemInformation`. | Pass `org.scijava.plugins.commands.debug.SystemInformation` as a string instead of a class to `ij.command().run()`. Removed import call. | --- |
| `GetVersionofMavenArtifact.java` | app | --- | --- | --- | --- |
| `DisplayError.java` | displays | --- | --- | --- | --- |
| `DisplayInfo.java` | displays | --- | --- | --- | --- |
| `DisplayWarning.java` | displays | --- | --- | --- | --- |
| `CommandThatChecksImageType.java` | extensions | --- | --- | --- | Runs in IJ1. |
| `ExampleCommand.java` | extensions | --- | No `main`. | --- | Dependency for `GetExampleCommandResults.java`. |
| `ExampleDynamicCommand.java` | extensions | --- | No `main`. | --- | Dependency for `ModifyCommand.java`. |
| `GetExampleCommandResults.java` | extensions | --- | --- | --- | --- |
| `ListAllCommands.java` | extensions | --- | --- | --- | --- |
| `ModifyCommand.java` | extensions | --- | --- | --- | Runs in IJ1. |
| `RunExampleCommand.java` | extensions | --- | --- | --- | Runs in IJ1. |
| `StartImageJHeadless.java` | headless | ---| --- | --- | --- |
| `ConvertImageClasses.java` | images | --- | --- | --- | --- |
| `CreateImage.java` | images | --- | --- | --- | --- |
| `DuplicateImage.java` | images | --- | --- | --- | --- |
| `GetOpenImages.java` | images  | --- | Input image drawn incorrectly (legacy bug). | Added `ij.ui().showUI("swing")` to `main`. | Displays image correctly when IJ2 interface enforced. | 
| `OpenAndShowImage.java` | images | **Y** | Input image drawn incorrectly (legacy bug). | --- | Displays image window only. |
| `SaveImage.java` | images | **Y** | Input image drawn incorrectly (legacy bug). | --- | Calls AWT UI. Saved image looks correct, displayed image incorrect. |
| `DrawCircle.java` | images > drawing | **Y** | --- | --- | Displays image window only. |
| `DrawRectangle.java` | images > drawing | **Y** | --- | --- | Displays image window only. |
| `CountCels.java` | images > processing  | **Y** | --- | --- | Displays image window and terminal output. |
| `ProcessChannelsIndividually.java` | images > processing | **Y** | --- | --- | Displays image window only. |
| `CallService.java` | services | **Y** | --- | --- | No UI. Terminal output only. |
| `ListAllServices.java` | services | **Y** | --- | --- | No UI. Terminal output only. |
| `CreateTable.java` | tables | **Y** | --- | --- | Displays table only. |
| `SaveAndLoadTable.java` | tables | **N** | Can't display table, throws `io.scif` exceptions. | --- | Does not display table with `pom.xml` version 26.0.0. Displays table (with exceptions) with `pom.xml` version 28.0.0. |
| `AskForFile.java` | userinput | **Y** | --- | --- | Displays file chooser only. |
| `AskYesNo.java` | userinput | **Y** | --- | --- | Displays dialog box only. |
| `ValidateParamter.java` | userinput | **Y** | --- | --- | Calls AWT UI. |

</center>