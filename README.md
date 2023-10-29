# calculadora-distribuida-con-rmi y sockets

Proyecto de Sistemas Operativos Distribuidos en Java: Implementación de Multidifusión (Multicast) RPC con Replicación de Servidores, TESE Ecatepec, hecho por alumno de 
ESCOM

## Descripción del Proyecto

En este proyecto, los estudiantes implementarán un sistema sencillo que permite la comunicación mediante RPC (Remote Procedure Call) utilizando la técnica de multidifusión (multicast) en un entorno distribuido. El sistema incluirá la replicación de servidores para mejorar el rendimiento y la tolerancia a fallos. El objetivo principal es proporcionar a los clientes una forma de comunicarse con los servidores replicados de manera transparente, como si estuvieran interactuando con un único servidor.

## Requisitos del Proyecto

### Implementación de Servidores

Los estudiantes deberán desarrollar varios servidores replicados en Java. Cada servidor proporcionará un conjunto de procedimientos remotos que los clientes podrán invocar mediante RPC.
Los servidores deben ser capaces de manejar las solicitudes de los clientes de manera eficiente y efectiva.
La replicación de servidores se utilizará para mejorar el rendimiento del sistema.

### Implementación de Clientes

Los estudiantes desarrollarán clientes que se comuniquen con los servidores mediante RPC.
El cliente deberá ser capaz de enviar una solicitud RPC que se distribuirá a todas las réplicas de los servidores de manera transparente. Para el cliente, parecerá que está enviando una única solicitud RPC.
Se deberá implementar una estrategia de multidifusión para lograr este comportamiento.

### Tolerancia a Fallos

Los servidores serán susceptibles a fallas. Los estudiantes deben implementar mecanismos para detectar y manejar estas fallas de manera que el sistema continúe funcionando de manera eficiente.

## Pruebas y Evaluación

Los estudiantes deberán realizar pruebas exhaustivas de su sistema, incluyendo escenarios de replicación y fallas en servidores, para asegurarse de que funcione correctamente.
Deberán documentar los procedimientos de prueba y los resultados obtenidos.

## Documentación

Se espera que los estudiantes entreguen una documentación completa que describa la arquitectura del sistema, las decisiones de diseño tomadas, el funcionamiento del sistema y las instrucciones para compilar y ejecutar el código.

## Recursos Recomendados

Se recomienda a los estudiantes utilizar bibliotecas o frameworks de manejo de RPC en Java para simplificar la implementación del sistema, como RMI (Java Remote Method Invocation) o gRPC.
Pueden explorar bibliotecas de multicast en Java para la implementación de la comunicación multicast eficiente.
Criterios de Evaluación: Los proyectos serán evaluados en función de la funcionalidad, eficiencia y robustez del sistema implementado, así como la calidad de la documentación proporcionada.

## Sobre la implemntacion

La implementacion elegida fue crear un servidor RMI que sea capaz de responder a las solicitudes, este servidor llamado Calculadora puede ser replicado las veces que sea necesario, tambien se creo un serverRegistry que atendera a las solicitudes del cliente y las reenviara a los servidores Calculadora para mejorar la eficiencia.

### Servidor Calculadora

La implemntacion consiste en inicar un servidor RMI con un onjeto remoto calculadora que realizara las operaciones necesarias, el servidor intentara registrarse en
Server Registry una vez que se inicie la replica, si la conexion falla realizara 2 intentos mas con un lapso de 5 segundos entre cada intento. Si no se pudo registrar se detiene el proceso completamente.
Se usa una gramatica para detectar las operaciones,

#### Gramatica usada

expression: term addop expression | term;
term: factor mulop term | factor;
factor: power powop factor | power;
power: '(' expression ')' | negexp | NUMBER | neg NUMBER | FUNCTION '(' expression ')' | neg FUNCTION '(' expression ')';
addop: '+' | '-';
mulop: '*' | '/';
powop: '^';
neg: '-' ;
negexp: neg '(' expression ')';

FUNCTION: 'sin' | 'cos' | 'tan' | 'sqrt' | 'pow';
NUMBER: DIGIT+ ('.' DIGIT+)?;

DIGIT: [0-9];

### Server Registry (Bus de mensajes)

Usara un servidor RMI donde tendra un objeto remoto a traves del cual se pueden regisrar las replicas, cada que se registre una nueva replica se llama al RMI de la replica para guardar una instancia de la calculadora en una cola, esta cola es importante ya que nos ayudara a distribuir las solicitudes entrantes.
Despues de que se inicie el servidor RMI del bus se inicia un Server Socket que manejara las solicitues de los clientes, sera un hilo por cada peticion,
cuando se inicia una nueva peticion se desencola un servidor de la cola de replicas, una vez que se finaliza la solicitus se vuelve a encolar el servidor.
Se incluye manejo de errores en caso de que el servidor este caido se eliminara de la cola de replicas.

### Cliente

El cliente realizara varias peticiones, para cada peticion debe abrir un nuevo socket, se implementara intentos de reconexion en caso de que no haya comunicacion con el bus de mensajes.
