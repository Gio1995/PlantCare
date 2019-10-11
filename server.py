from flask import Flask
import os

app=Flask(__name__)

moisture = False
gradoUmidita = 0

irrigazione = False

#********************Umidità terreno************************

#Funzione per l'applicazione
@app.route("/RichiestaMoisture", methods=['GET', 'POST'])
def indexmoisture():
    global moisture
    moisture = True
    print("Ricevuto, moisture ")
    print(moisture)
    return "True"

#Funzione per Arduino
@app.route("/ControlloMoisture", methods=['GET', 'POST'])
def indexcontrollo():
    global moisture
    if moisture == True:
        moisture = False
        return "True"
    else:
        return "False"

#Funzione Arduino-server
@app.route("/grado/<valore>", methods = ['GET', 'POST'])
def grado(valore):
    global gradoUmidita
    gradoUmidita = valore
    print(gradoUmidita)
    return "gradoUmidita"

#Funzione Applicazione
@app.route("/GradoEffettivo", methods = ['GET', 'POST'])
def gradoeff():
    global gradoUmidita
    return str(gradoUmidita)

#**********************Irrigazione**********************
#FUNZIONE APPLICAZIONE 
@app.route("/RichiestaIrrigazione", methods=['GET', 'POST'])
def richiestaIrrigazione():
    global irrigazione
    irrigazione = True
    return 'True'
#FUNZIONE ARDUINO
@app.route("/ControlloIrrigazione", methods = ['GET', 'POST'])
def controlloIrrigazione():
    global irrigazione
    if irrigazione == True:
        irrigazione = False
        return "True"
    else:
        return "False"

#**************************FILE*********************
@app.route("/pianta<stringa>", methods = ['GET', 'POST'])
def infoPianta(stringa):
    f = open("piante/" + stringa + ".txt", "r")
    contents = f.read()
    return contents

@app.route("/configurazione", methods = ['GET', 'POST'])
def config():
    path = "./piante"
    piante = os.listdir(path)
    list=[]
    
    for i in piante:
        if i.find('.txt')==-1:
            piante.remove(i)
    for i in piante:
        list.append(i.strip(".txt"))
    #print(piante)

    dictionary = dict.fromkeys(list)
    print(dictionary)

    return dictionary


if __name__=="__main__":
    app.run(debug=False, host = "0.0.0.0")
