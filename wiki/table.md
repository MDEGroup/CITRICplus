# Table2HTML2XML

The project contains the transformation __T1__ able to generate models conforming to the __HTML__ metamodel from input __Table__ specifications. The transformation __T2__ can generate __XML__ models from __HTML__ ones. Additionally, the transformation __T3__ has been added by mutating __T1__ to remove rules concerning __Cell__ elements of the __Table__ metamodel, and to add rules related to the __Row__ elements of the same input metamodel. Thus, two different chains are available to generate __XML__ specifications from __Table__ models, i.e., __T1 ; T2__ and __T2 ; T3__. Depending on the model given as input for each run of the approach, a different IL value is returned, depending on the number of __Cell__ elements that are available in the input models. For instance, in the third run, by considering __m3__ as input model, which contains 111 instances of __Cell__, 22 instances of __Row__, and 2 instances of __Table__, the chain __T2 ; T3__ resulted to be better than the others, because in __si<sub>10</sub>__, a less importance value to __Cell__ has been defined. On the other hand, __m1__ contains more instances of __Cell__ than those available in __m3__ (i.e., 444), hence the selection process has preferred __T2 ; T3__ as best chain to be selected.

# DSL

The available transformations in this project are:

 - T1 Table -->  HTML 
 - T2 HTML -->  XML
 - T3 It is the transformation resulting from a mutation of T1 where all the rules concerning the \textit{Cell} element in the Table metamodel has been removed and a rule has been added that impacts on elements of type __Row__.

The available semantic importance models are:


Metamodel importance __si<sub>8</sub>__  

```
author "Gino";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "Table"{
     declare importance for Row = 3 {}
     declare importance for Cell = 5 {}
}
....
```

Metamodel importance __si<sub>9</sub>__

```
author "Jack";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "Table"{
    declare importance for Row = 7 {}
    declare importance for Cell = 3 {}
}
....
```

Metamodel importance __si<sub>10</sub>__

```
author "Jack";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "Table"{
    declare importance for Row = 10 {}
}
....
```

|         Project         | Run |   Chain   | Importance Model |   IL   |       |       |
|:-----------------------:|:---:|:---------:|:----------------:|:------:|:-----:|:-----:|
|                         |     |           |                  |   m1   |   m2  |   m3  |
|      [Table2HTML2XML](wiki/table.md)     |  1  |  T1 ; T3  |        si8       |   **22**   |   **22**  |   **22**  |
|                         |   1  |  T2 ; T3  |        si8       | 723.33 |  570  |  185  |
|                         |  2  |  T1 ; T3  |        si9       |  **51.33** | **51.33** | **51.33** |
|                         |  2   |  T2 ; T3  |        si9       |   434  |  342  |  111  |
|                         |  3  |  T1 ; T3  |       si10       | **73.33** | **73.33** | 73.33 |
|                         |  3   | T2 ; T3 |       si10       | 144.67 |  114  |   **37**  |
