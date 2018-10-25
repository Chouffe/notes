# pdftk

* Split files
```
pdftk <pdf.pdf> burst
```
* Remove pages from a pdf - pages 10 to 25
```
pdftk myDocument.pdf cat 1-9 26-end output removedPages.pdf
```
* Joining files together: `cat` stands for concatenate
```
pdftk file1.pdf file2.pdf cat output newFile.pdf
```

## Resources

* [Manipulating pdfs with pdf toolkit](https://www.linux.com/learn/manipulating-pdfs-pdf-toolkit)
