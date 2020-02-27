ImageJ maven-projects > howto migration notes
===

<center>

### **Testing maven-project migrated files (howto):**

| File name | Moved from (maven-projects) | Moved to (howto) | Runs (Y/N) | Issues | Action taken | Migrated (Y/N) |Notes |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| `AddROIs.java` | add-rois | images | **Y** | ROIs do not display on image until interacted with. | --- | **Y** | Calls swing UI. |
| `AddTwoDatasets.java` | add-two-datasets | --- | **Y** | Module throws exception if images are not extactly the same `java.lang.IllegalArgumentException: No matching 'net.imagej.ops.Ops$Math$Add' op`. | --- | **N** | Calls AWT UI. Outputs 1 of 2 datasets as a composite image. Likely not what its supposed to do. |
| `DisplayATable.java` | call-modern-from-legacy | adv | **Y** | --- | Renamed class to `ModernFromLegacy` and file name to `ModernFromLegacy.java` | **Y** | Calls AWT UI. Opens swing table. |
| `CreateANewOp.java` | create-a-new-op | ops | **Y** | --- | --- | **Y** | No UI. Terminal output only. |
| `Ramp.java` | create-a-new-op | ops | **Y** | --- | Renamed file to `RampOp.java`. | **Y** | Calls AWT UI. |
| `RandomBlobs.java` | create-a-new-op | ops | **Y** | --- | Renamed file to `RandomBlobsOp.java` | **Y** |Calls AWT UI. |
| `Animal.java` | create-a-new-plugin-type | plugins > create | **N** | --- | --- | **Y** | Dependency for `CreateANewPluginType.java`. |
| `AnimalService.java` | create-a-new-plugin-type | plugins > create | **N** | --- | --- | **Y** | Dependency for `CreateANewPluginType.java` |
| `Bear.java` | create-a-new-plugin-type | plugins > create | **N** | --- | --- | **Y** | Dependency for `CreateANewPluginType.java` |
| `CreateANewPluginType.java` | create-a-new-plugin-type | plugins > create | **Y** | --- | --- | **Y** | No UI. Terminal output only. |
| `Lion.java` | create-a-new-plugin-type | plugins > create | **N** | --- | --- | **Y** | Dependency for `CreateANewPluginType.java` |
| `Tiger.java` | create-a-new-plugin-type | plugins > create | **N** | --- | --- | **Y** | Dependency for `CreateANewPluginType.java` | 
| `CommandWithPreview.java` |commands-with-preview | ui > preview | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `PreviewCheckbox.java` | commands-with-preview | ui > preview | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `TrollPreprocessor.java` | custom-preprocessor-plugin | --- | **Y** | Runs for each command for all `.java` files calling UI. Annoying! | --- | **N** | Calls AWT UI. |
| `DynamicCallbacks.java` | dynamic-commands | --- | **Y** | Opens UI. Selecting options either returns input letter (a, b or c) or returns only the first item in the `kindOfThing` list. | --- | **N** | Calls AWT UI. |
| `DynamicInitialization.java` | dynamic-commands | commands > dynamic | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `DynamicNumberOfParameters.java` | dynamic-commands | commands > dynamic | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `ExecuteCommands.java` | execute-commands | --- | **N** | Broken import of `org.scijava.plugins.commands.io.OpenFile` | --- | **N** | --- |
| `DatasetWrapping.java` | ij2-image-plus | --- | **Y** | Doesn't seem to do anything, opens blank image. | --- | **N** | Calls AWT UI. |
| `IntroToImageJAPI.java` | intro-to-imagej-api | adv | **Y** | --- | --- | **Y** | No UI. Opens imagej.net webpage and terminal output. |
| `ListenToEvents.java` | listen-to-events | --- | **Y** | AWT image window does not output events to the terminal, swing image window does. | Possible action: Request swing UI by calling `ij.ui().showUI("swing")`. | **N** | Calls AWT UI (image window only). |
| `LoadAndDisplayDataset.java` | load-and-display-dataset | datasets | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** | Calls AWT UI (image window only). |
| `LowPassFilter.java` | low-pass-filter | images > filtering | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** |Calls AWT UI. |
| `GetMetadata.java` | metadata | metadata | **Y** | Input image drawn incorrectly (legacy bug) | --- | **Y** | Calls AWT UI. |
| `CopyLabels.java` | mixed-world-command | commands > simple | **Y** | --- | --- | **Y** | Calls AWT UI. |.
| `GradientImage.java` | simple-commands | commands > simple | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `HelloWorld.java` | simple-commands | commands > simple | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `OpenImage.java` | simple-commands | commands > simple | **Y** | Input image drawn incorretly (legacy bug). | --- | **Y** | Calls AWT UI. |
| `OpenScaleSaveImage.java` | simple-commands | commands > simple | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `DeconvolutionCommand.java` | swing-example | --- | --- | --- | --- | **N** | Depedency for `DeconvolutionDialog.java`. |
| `DeconvolutionCommandSwing.java` | swing-example | --- | --- | --- | --- | **N** |Dependency for `DeconvolutionDialog.java`. |
| `DeconvolutionDialog.java` | swing-example | --- | **Y** | OK and Cancel buttons do not work. Crashes after interaction. | --- | **N** |**Do not migrate until workout bug issue** |
| `SwingExample.java` | swing-example | ui | **Y** | Called swing ui via `ij.ui().showUI("swing")`. | --- | **Y** | Calls swing UI. |
| `TableTutorial.java` | tables | tables | **Y** | --- | --- | **Y** | Calls AWT UI |
| `ConvolutionOps.java` | using-ops | ops | **Y** | --- | --- | **Y** | Displays image windows only. |
| `UsingOps.java` | using-ops | ops | **Y** | --- | --- | **Y** | Displays image window and terminal output. |
| `UsingOpsDog.java` | using-ops |  ops | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** | Calls AWT UI. |
| `UsingOpsLabeling.java` | using-ops | ops | **Y** | Input image drawn incorrectly (legacy bug). | --- | **Y** | Calls AWT UI. |
| `UsingSpecialOps.java` | using-ops |ops | **Y** | --- | --- | **Y** | Has commented out code block. Remove? |
| `WidgetDemo.java` | widget-demo | ui | **Y** | --- | --- | **Y** | Calls AWT UI. |
| `WorkingWithModules.java` | working-with-modules | --- | **N** | Missing `images/about` path and throws exception | --- | **N** | --- |


### **Testing howto java files:**

| File name | Location | Runs (Y/N) | Issues | Action taken  | Notes |
| :---: | :---: | :---: | :---: | :---: | :---: |
| `DisposeImageJ.java` | app | **N** | No `main`. | --- | --- |
| `GetImageJDirectory.java` | app | **Y** | --- | --- | No UI. Terminal output only. |
| `GetJARsInClassPath.java` | app | **Y** | --- | --- | No UI. Terminal output only. |
| `GetSystemInformation.java` | app | **Y** | Broken import of `org.scijava.plugins.commands.debug.SystemInformation`. | Pass `org.scijava.plugins.commands.debug.SystemInformation` as a string instead of a class to `ij.command().run()`. Removed import call. | No UI. Terminal output only. |
| `GetVersionofMavenArtifact.java` | app | **Y** | --- | --- | No UI. Terminal output only. |
| `DisplayError.java` | displays | **Y** | --- | --- | Displays dialog box only. |
| `DisplayInfo.java` | displays | **Y** | --- | --- | Displays dialog box only. |
| `DisplayWarning.java` | displays | **Y** | --- | --- | Displays dialog box only. |
| `CommandThatChecksImageType.java` | extensions | **Y** | --- | --- | Calls AWT UI. |
| `ExampleCommand.java` | extensions | **N** | --- | --- | Dependency for `RunExampleCommand.java` and `GetExampleCommandResults.java`. |
| `ExampleDynamicCommand.java` | extensions | **N** | --- | --- | Dependency for `ModifyCommand.java`. |
| `GetExampleCommandResults.java` | extensions | **Y** | --- | --- | No UI. Terminal output only. |
| `ListAllCommands.java` | extensions | **Y** | --- | --- | No UI. Terminal output only. |
| `ModifyCommand.java` | extensions | **Y** | --- | --- | Calls AWT UI. |
| `RunExampleCommand.java` | extensions | **Y** | --- | --- | Calls AWT UI. |
| `StartImageJHeadless.java` | headless | **Y** | --- | --- | No UI. Terminal output only. |
| `ConvertImageClasses.java` | images | **Y** | --- | --- | No UI. Terminal output only. |
| `CreateImage.java` | images | **Y** | --- | --- | No UI. Terminal output only. |
| `DuplicateImage.java` | images | **Y** | --- | --- | No UI. Terminal output only. |
| `GetOpenImages.java` | images  | **Y** | Input image drawn incorrectly (legacy bug). | --- | Displays image window only. | 
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