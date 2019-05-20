import pytesseract

def imgToText(im):
    text = pytesseract.image_to_string(im, lang='eng')
    print("Desde Aquí")
    print(text)
    print("Hasta Aquí")