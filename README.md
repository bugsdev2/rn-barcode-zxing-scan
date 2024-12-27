# rn-barcode-zxing-scan

A fork of react-native-barcode-zxing-scan, since the original wasn't returning anything and the documentation was wrong. Fixed both here.

The zxing module scanning barcodes in android.

This module abstracts the library zxing-android-embedded, developed by JourneyApps.

## Barcode formats:

- CODE 128
- UPC A
- UPC E
- EAN 8
- EAN 13
- RSS 14
- CODE 39
- CODE 93
- ITF
- RSS EXPANDED
- QR CODE
- DATA MATRIX
- PDF 417

## Getting started

`$ npm install rn-barcode-zxing-scan --save`

## Mostly automatic installation

`$ react-native link rn-barcode-zxing-scan`

## Manual installation

Android
Update rn to 0.60.\* and use autolink

## Usage

#### App.js

```
import React from "react";
import { Text, View, TouchableOpacity } from "react-native";
import BarcodeZxingScan from "rn-barcode-zxing-scan";

const App = () => {

  const handleClick = () => {
    BarcodeZxingScan.showQrReader((error, data) => {
      if (error) {
        console.error('Error:', error);
      } else {
        console.log('Barcode:', data);
      }
    };

  return (
    <View>
      <TouchableOpacity
        onPress={() => handleClick()}
        style={{
          margin: 20,
          backgroundColor: "blue",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Text style={{ fontSize: 30 }}>SCAN</Text>
      </TouchableOpacity>
    </View>
  );
};

export default App;
```
