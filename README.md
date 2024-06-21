# Glucofie - Machine Learning Path
Bangkit 2024 Product-based Capstone

Team ID: C241-PS241

(ML) M006D4KX1798 – Azza Annathifa – Universitas Brawijaya

(ML) M180D4KX1858 – Rizkiyatun Nafisah – Universitas Airlangga

(ML) M315D4KX2160 – Siti Nurhaliza – Universitas Sriwijaya

# Nutrition Label Detection and Extraction

This project involves collecting packaging data that includes a nutrition table, performing data labeling using Roboflow, and augmenting the data to increase variability. A detection model is then created using YoloV8 with the collected dataset. Finally, the required values are extracted using OCR and regex, and the detection model is converted to TensorFlow.Js format.

## Table of Contents

- [Introduction](#introduction)
- [Data Collection](#data-collection)
- [Data Labeling](#data-labeling)
- [Data Augmentation](#data-augmentation)
- [Model Training](#model-training)
- [Value Extraction](#value-extraction)
- [Model Conversion](#model-conversion)

## Introduction

The purpose of this project is to create a robust system that can detect and extract nutritional information from food packaging images. This system will utilize machine learning and computer vision techniques to achieve its goals.

## Data Collection

Collect packaging images that contain a nutrition table. These images will form the basis of our dataset.

## Data Labeling

Use [Roboflow](https://roboflow.com) to label the collected images. Label the nutritional information table and other relevant parts of the packaging.

## Data Augmentation

To enhance the variability and robustness of the dataset, perform data augmentation techniques such as:
- Adding noise
- Image rotation
- Blur
- Saturation adjustment

## Model Training

Train a detection model using YoloV8 with the augmented dataset. YoloV8 is chosen for its high accuracy and performance in object detection tasks.

## Value Extraction

After detecting the nutritional information table, use OCR (Optical Character Recognition) and regex (regular expressions) to extract the required values from the detected table.

## Model Conversion

Convert the trained YoloV8 detection model to TensorFlow.Js format
