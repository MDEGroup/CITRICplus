# Grafcet2PetriNet2PNML

In this project, there is the transformation __T1__ able to generate __PetriNet__ models from __Grafcet__ specifications.  The transformation __T2__ generates __PNML__ models from __PetriNet__ specifications. The transformation __T3__ is a mutation of T1, in which any rule referring to the __Transition__ metaclass in the __Grafcet__ metamodel was removed. Thus, given an input __Grafcet__ model,  two  chains are available, i.e.,  __T1 ; T2__ and __T3 ; T2__, each inducing a different information loss. For instance, by using the model m3 as input, increasing the semantic importance of __Transition__ (as done in __si<sub>4</sub>__) it can be seen that the best chain changes between the first run and the second one: in the first execution the best chain is __T1 ; T2__, whereas in the second one the best chain is __T3; T2__.

## DSL

The available transformations in this project are:

 - T1 Grafcet --> PetriNet 
 - T2 PetriNet --> PNML
 - T3 It is the transformation resulting from a mutation of T1 where all the rule concerning the __Transition__ element in the __Grafcet__ metamodel has been removed.

The available semantic importance models are:

 Metamodel importance __si<sub>4</sub>__

```
author "Dany";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "Grafcet"{
    declare importance for Transition = 4 {}
}
....
```

Metamodel importance __si<sub>5</sub>__
```
author "Dany";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "Grafcet"{
    declare importance for Transition = 2 {}
    declare importance for Transition = 7 {}
}
....
```
