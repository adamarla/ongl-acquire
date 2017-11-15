#!/bin/bash

for file in files/*.pdf; do

    pdf=$(basename $file)
    txt=${pdf/pdf/txt}

    if [ -e ocr/$txt ]; then
        echo "skipping... " $pdf 
        continue
    fi

    echo "processing... " $pdf

    mkdir tmp
    cd tmp

    cp ../files/$pdf .

    pdfimages -f 1 -l 2 -png $pdf pg
    for png in pg*.png; do
        tesseract $png page # tesseract adds .txt extension
        cat page.txt >> $txt
        rm page.txt
    done
    mv $txt ../ocr/
    cd ..
    rm -rf tmp/

done

