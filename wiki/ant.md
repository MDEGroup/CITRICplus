# ANT2Maven2XML

This project contains the transformation __T1__ to generate __Maven__ specifications from __ANT__ ones. 
The transformation __T2__ can generate __XML__ models from input __Maven__ specifications. The additional transformation __T3__ has been obtained from a mutation of __T1__ by removing the rule referring to the __Target__ metaclass of the __ANT__ metamodel. Thus, to bridge the __ANT__ and __XML__ two different chains are possible, i.e., __T1 ; T2__ and __T3 ; T2__.

Concerning the three executions of the approach on this project, the result in terms of the best-selected chain is always __T3 ; T2__, even though it is still possible to notice the variation of the information loss values depending on the considered semantic importance specification, and the given input model. For instance, by considering the model __m2__ as input, the information loss value increases from 5.6 to 8.8 due to the different semantic importance given to the __Target__ metaclass by the __si<sub>6</sub>__ specification.

## DSL

The available transformations in this project are:

 - T1 ANT --> Maven
 - T2 Maven --> XML
 - T3 It is the transformation resulting from a mutation of T1 where all the rule concerning the __Target__ element in the __ANT__ metamodel has been removed.

The available semantic importance models are:


Metamodel importance __si<sub>6</sub>__
```
author "Dave";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "Ant"{
    declare importance for Target = 5 {}
}

....
```

Metamodel importance __si<sub>7</sub>__
```
author "Dave";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "Ant"{
     declare importance for Path = 4 {}
}

....
```

|         Project         | Run |   Chain   | Importance Model |   IL   |       |       |
|:-----------------------:|:---:|:---------:|:----------------:|:------:|:-----:|:-----:|
|                         |     |           |                  |   m1   |   m2  |   m3  |
|      [ANT2Maven2XML](wiki/ant.md)      |  1  |  T3 ; T2  |      default     |   **8.4**  |  **5.6**  |  **8.4**  |
|                         |   1  |  T1 ; T2  |      default     |  62,59 | 62.59 | 45.52 |
|                         |  2  |  T3 ; T2  |        si6       |  **13.2**  |  **8.8**  |  **13.2** |
|                         |  2   |  T1 ; T2  |      default     |  62,59 | 62,59 | 45,52 |
|                         |  3  |  T3 ; T2  |        si6       |  **13.2**  |  **8.8**  |  **13.2** |
|                         |  3   |  T1 ; T2  |        si7       |  75.1  |  75.1 | 54.62 |

