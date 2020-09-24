# Project-yu-shi
Project-yu-shi created by GitHub Classroom

# Number Recognizer

This project demonstrates how to use JavaFX to build an application for handwritten
digits classification from MNIST.


![alt text](https://github.com/NEU-GradStudents/group-project-yu-shi/blob/master/Screen%20Shot%202020-04-20%20at%2011.23.35%20PM.png)

![alt text](https://github.com/NEU-GradStudents/group-project-yu-shi/blob/master/Screen%20Shot%202020-04-20%20at%2011.25.59%20PM.png)









## How to build

**Prerequisites:**

```
● Install Python latest version here: ​https://www.python.org/downloads/
● Install JDK11 & up here:
https://www.oracle.com/java/technologies/javase-downloads.html
● Getting started with JavaFX here: ​https://openjfx.io/openjfx-docs/#install-java
● Getting started with SceneBuilder here:
https://gluonhq.com/products/scene-builder/
● Install Tensorflow for Java here: ​https://www.tensorflow.org/install/lang_java
● Install Tensorflow for Python here: ​https://www.tensorflow.org/install/pip​ or install
Tensorflow in Pycharm
● No Maven/Gradle required.
```

**Step 1 Training**

1. Install Tensorflow in Pycharm

2. Load MNIST dataset -> reshape data -> normalize data

3. Construct model & train -> save as .pb file

```
The input layer is a 4-d array, the output layer is a 2-d array.
******************************************************************
Input layer 4-d: [BATCH_SIZE] [HEIGHT] [WIDTH] [PIXEL]
Output layer 2-d: [BATCH_SIZE] [PROBABILITIES]
******************************************************************
```
**Step 2 Build JavaFX GUI (MVC)**

1. Download, install & configure SceneBuilder2.0 in IntelliJ or other IDE

2. Instead of writing fxml from scratch, use SceneBuilder to auto-generate fxml
    files.
    
3. Define GUI behaviors in Controller.java
    (e.g. button click, canvas snapshot)
    
    checkpoint: save canvas snapshot

**Step 3 Reshape image & convert to normalized 4-d array**

1. Convert image to grayscale
2. Reshape image to 28 * 28 pixel
3. Get normalized 3-d array from image
    3-d: [HEIGHT] [WIDTH] [PIXEL]


4. Add a 4th dimension to 3-d array, the 4th dimension stands for BATCH_SIZE
    
    In this case, BATCH_SIZE = 1
    
    4-d: [1] [HEIGHT] [WIDTH] [PIXEL]

**Step 4 Load MNIST model from .pb file and serve**

1. Load .pb as a servable model

2. Create an input tensor with 4-d array

3. Feed the model with input Tensor and fetch output

4. Extract the index with max value

5. Map index to predicted result(digit)

**Youtube Channel**

https://www.youtube.com/channel/UC3nf51hFUv7Mz1kufxPe55A/videos?view_as=subscriber
