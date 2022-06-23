# TP-Final-Objetos-2
Trabajo final de objetos 2 de la Universidad Nacional de Quilmes

## Refactor
### UML:
- [x] Relación entreFilterVerificationModeyEnumde Voteesta duplicada (Consultar???)
- [x] IFlterInsectyGenericOpinioTypepasa algo similar.
- [x] KnowledgeStatusUpdate, ningún objeto conoce esa interface.¿Tiene sentido que el objetoKnowledgela implemente? Una muestra es que en el documento donde explican los roles de las interfaces y clases, esa interfaz queda sin rol. Revisar este modelado.

### Implementacion:
- [x] Filtrar las opiniones de los usuario, no debería de hacerse a través de un Id, sino comparar la instancia del objeto. Esto constituye un error en relación al concepto de Identidad en POO. Los objetos no necesitan un “id” para ser identificados. Reemplazar las búsquedas por Id por comparación por identidad.
  - [ ] Corregir tests
- [x] addVerifySampleno tiene sentido, porque lo únicoque hace es llamar a notifyVerifySample
  - [ ] Corregir tests
- [ ] **Location**: Último mensaje definido en la clase no es necesario.
  - Esto se pide en el TP en la parte de ubicaciones
- [ ] Dentro de la implementación del patrón State se repite código en getLevelVeificationpara algunas de las subclases.
- [ ] No delegan del todo bien en el estado de la muestra la forma de obtener la opinión.
- [x] **BinaryFilter**: Error en la construcción del objeto. El objeto está recreado de manera incompleta después de instanciarlo, los valores de los filtros podrían ser nulos.
- [x] El nivel de conocimiento tiene que ser recalculado en el getKnowdgele

### Tests: </br>
En general los tests testean solo el “camino feliz” y no prueban casos bordes. Pero además las aserciones no son suficientes para probar cada caso.
Ejemplos:
En <ins>WebApplicationTest</ins> acertar que el size de la listaes mayor a 0 pero no alcanzaría para probar que el usuario fue registrado. 
<ins>CoverageAreaTest>>#NotifyVerifySampleTest</ins> la aserciónno es correcta, no alcanza con verificar que la lista de observadores no está vacía, deberían verificar que a cada uno de los observadores se le haya enviado el mensaje validateSampleluego del excersice del test.
<ins>CoverageAreaTest>>#testAddNewSampleWhenCoverageAreaHaveOrganizationObsevers</ins> la asercion deberia ser sobre los observadoressi le llevo el mensaje uploadNewSample

### Documento: </br>
Falta definir las clases concretas en los states y queda en evidencia que hay una interfaz que no tiene relación ni con el modelo ni con el patrón.

### Mejoras generales:
En <ins>registerCoverageArea</ins> se puede hacer una única iteraciónpara agregar el elemento si corresponde. Que hay que hacer?
Revisar nombre de las clases y variables para no atarse a una implementación de un patrón.
Ejemplo: Observer.<ins>SampleStateImpl</ins> nombre poco claro.
SampleStateImpl relación con la interface <ins>UserValdations</ins>,no es necesaria,
porque ningún otro objeto conoce a la interface
