# Animated Path Finder

![intro](https://user-images.githubusercontent.com/55555187/102256573-d70bd880-3f0b-11eb-829d-8b48b4b3f91d.gif)

## Como descargarlo y ejecutarlo


## ¿Que es el pathfinding?

El pathfinding es una técnica cuyo objetivo es encontrar un camino de un punto a otro lo más rápida y eficientemente posible.
En esta aplicación se han empleado dos algoritmos de pathfinding con el fin de realizar una comparativa visual entre ellos.

### A*

A* es uno de los algoritmos de búsqueda más usados en la actualidad, se trata de un algoritmo voraz que utiliza heurística para mejorar la eficiencia de la búsqueda.
En este caso se emplean 2 heurísticas diferentes dependiendo de las restricciones de movimiento (se le ofrece al usuario la posibilidad de deshabilitar las diagonales).

![gif A intro](https://user-images.githubusercontent.com/55555187/102255212-0cafc200-3f0a-11eb-9d64-72d70b1f030f.gif)

### BFS

BFS es un algoritmo de búsqueda en anchura que se utiliza en grafos no valorados para hallar el camino mínimo entre dos puntos.

![gif BFS intro](https://user-images.githubusercontent.com/55555187/102255239-1507fd00-3f0a-11eb-830e-60726ea09cdb.gif)

## Características de la aplicación

### Panel interactivo

 A través del teclado y el ratón se puede interactuar con la aplicación de modo que su utilización sea muy amigable con el usuario, se pueden realizar las siguiente acciones:
 
 #### Crear muros
 
 ![muros](https://user-images.githubusercontent.com/55555187/102259205-18ea4e00-3f0f-11eb-949b-ccdca699e1df.gif)

 (click derecho)
 #### Borrar muros
 
 ![borrar](https://user-images.githubusercontent.com/55555187/102259209-1ab41180-3f0f-11eb-8568-fd95773d7575.gif)

 (click izquierdo)
 #### Desplazar los puntos de comienzo y final 

![puntos](https://user-images.githubusercontent.com/55555187/102259214-1be53e80-3f0f-11eb-9401-07fb9fcb34ed.gif)

 (punto de comienzo -> S + click izquierdo   punto de final -> E + click izquierdo)

#### Animación de la solución
 En el panel se muestra una animación que permite visualizar el funcionamiento del algoritmo que se halla elegido para realizar la búsqueda, la animación consta de 2 fases:
 
 En la primera fase se pintan los nodos que el algoritmo ha visitado mientras buscaba el camino del punto de comienzo al punto de final.
 En la segunda fase se muestra el camino que el algoritmo ha encontrado.
 
### Panel de control

![panel de control](https://user-images.githubusercontent.com/55555187/102259875-f3aa0f80-3f0f-11eb-9310-1ba824ac6438.png)

#### Delay ajustable
Permite seleccionar la velocidad a la que la animación se muestra en el panel.

#### Selector de diagonales
Permite deshabilitar las diagonales a la hora de realizar la búsqueda lo cual afecta al camino encontrado por el algoritmo de búsqueda.

###### Sin diagonales

![sin diagonales](https://user-images.githubusercontent.com/55555187/102260458-c0b44b80-3f10-11eb-8070-8287b6801d04.gif)    

###### Con diagonales

![con diagonales](https://user-images.githubusercontent.com/55555187/102260695-1852b700-3f11-11eb-9417-89221e963036.gif)

#### Reset del mapa sin perder los muros
Permite resetear el panel manteniendo los muros.

![Reset no walls](https://user-images.githubusercontent.com/55555187/102262302-30c3d100-3f13-11eb-96b8-bc5b1ac1461e.gif)

### Escalado automatico
La aplicación escala automaticamente dependiendo de la resolución de la pantalla en la que se ejecute.
