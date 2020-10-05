# Shapes 

Concerning the first execution on the project, the selected chain is __T2 ; T3__ for all the three given input models as highlighted in the corresponding IL values, which resulted to be always 1.07 (see the row corresponding to the first __Run__. For the other executions, some transformation mutations have been operated. In particular, __T1__ has been mutated by adding a new rule covering the  input __Square__ metaclass, and the output __Diamond__ one. Such mutation has lead to the transformation __T4__, which has been considered in the second execution. In particular, the selected chain resulted always __T4 ; T3__ with IL null for all the three models. Such result is due to the complete coverage that __T4__ has concerning the metaclasses of the input and target metamodels, then matching and producing all the elements in the models. For the third execution of the approach, two transformation mutations have been operated. In particular, __T5__ has been obtained by adding a new rule to __T2__ for covering the input __Circle__ metaclass, and the output __BlueCircle__ one. Moreover, T6 has been obtained by removing the rule __BlueCircle2Triangle__ from the transformation __T3__. By taking into account such mutated transformations, and by considering the same semantic importance specification, the calculated IL values for all three input models, shows that __T4 ; T3__ is still better as it has 0 as IL value. 

## DSL

The available transformations in this project are:

 - T1 ShapeMM1 --> ShapeMM2
 - T2 ShapeMM1 -->  ShapeMM2 (bis)
 - T3 ShapeMM2 --> ShapeMM3          
 - T4 It is the transformation resulting from a mutation of T1 where a new rule has been added, in particular, __Square2Diamond__.
 - T5 It is the transformation resulting from a mutation of T2 where a new rule has been added, in particular, __Circle2Blue-Circle__.
 - T6 It is the transformation resulting from a mutation of T3 where the rule __BlueCircle2Triangle__ has been removed.

The available semantic importance models are:


Semantic importance __si<sub>1</sub>__

```
author "Rob";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "ShapeMM1"{
    declare importance for Square = 2 {}
}

use metamodel "ShapeMM2"{
    declare importance for Diamond = 2 {}
    declare importance for BlueCircle = 2 {}
}
....
```

Semantic importance __si<sub>2</sub>__

```
author "John";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "ShapeMM1"{
    declare importance for Square = 2 {}
}

use metamodel "ShapeMM2"{
    declare importance for Diamond = 2 {}
    declare importance for BlueCircle = 5 {}
}
....
```

## Chaining results

|         Project         | Run |   Chain   | Importance Model |   IL   |       |       |
|:-----------------------:|:---:|:---------:|:----------------:|:------:|:-----:|:-----:|
|                         |     |           |                  |   m1   |   m2  |   m3  |
|[Shapes](wiki/shape.md)         |  1  |  T1 ; T3  |        si1       |  1.73  |  1.73 |  1.73 |
|                         |  1   |  T2 ; T3  |        si1       |  **1.07**  |  **1.07** |  **1.07** |
|                         |  2  |  T4 ; T3  |        si1       |   **0**   |   **0**   |   **0**   |
|                         |   2  |  T2 ; T3  |        si1       |  1.07  |  1.07 |  1.07 |
|                         |  3  |  T4 ; T3  |        si2       |    **0**   |   **0**   |   **0**   |
|                         |  3   |  T5 ; T6  |        si2       |  3.33  |  3.33 |  3.33 |
