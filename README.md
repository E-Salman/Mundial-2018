# Mundial 2018
Sistema que simula el mundial de futbol de Rusia del 2018. Brinda informacion diversa sobre el mismo, con principal enfoque en la busqueda del camino mas corto entre una ciudad y otra.

Se almacena un mapa de algunas de las ciudades de Rusia con sus datos y las rutas terrestres entre ellas con sus respectivas distancias. Este mapa es implementado a traves de un grafo etiquetado.

Los equipos de futbol se guardan en una tabla de busqueda implementada con arbol AVL, ordenada alfabeticamente por el nombre del pais.

Los partidos se almacenan con un mapeo con dominio <equipo1, equipo2>, ademas de otros datos relevantes del mismo.

Permite agregar nuevos elementos durante la ejecucion.
Los datos iniciales se cargan a partir del archivo input.txt; "E" indica que se carga un equipo, "C" una ciudad, "R" una ruta y "P" un partido. 
Para indicar el resto de los datos de cada uno, se hace en este formato:

//E: Pais; DT; Grupo; Puntos; Goles a favor; Goles en contra

//P: Pais1; Pais2; Ronda; Goles1; Goles2; Ciudad

//C: Ciudad; Superficie; Habitantes; esSede

//R: Ciudad1; Ciudad2; Distancia

![image](https://user-images.githubusercontent.com/71310857/149806193-4948e5ab-f0f2-480a-a6c8-7d25ef312892.png)

![image](https://user-images.githubusercontent.com/71310857/149807507-50bc3797-8cbf-4731-8c77-73ea6dcf39a2.png)

![image](https://user-images.githubusercontent.com/71310857/149807620-6fa7afc1-d295-484b-b2ab-cbc96a66f2c8.png)

Mostrar sistema ejemplo:
![image](https://user-images.githubusercontent.com/71310857/149807841-013e1b5d-5f01-4712-94b4-77bb197c0df6.png)
