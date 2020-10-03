# PathExp2PetriNet2XML

This project includes a transformation that takes as input __PathExp__ models and produces __PetriNet__ ones. Then, two alternative transformations can be applied to generate    models conforming to the __XML__ metamodel. Thus, two possible chains are available to generate __XML__ models from input __PathExp__ ones, i.e., __T1 ; T2__ and __T1 ; T3__     A mutation of the __T1__ transformation has been performed to obtain __T4__, in which all the rules referring to the __State__ metaclass have been removed. Such mutation has     been intentionally done to stress the approach on cases when model elements of high importance are not propagated to the next steps of the chain. According to the specificati    on __si<sub>3</sub>__, the semantic importance of __State__ elements is much higher than the other ones. Thus, depending on the input model in each run, a different informati    on loss value is returned, depending on the number of __State__ instances that are available in the input models. For instance, by considering __m1__ as input model in the th    ird run, __T1 ; T2__ resulted to be better than the other chain, since the input model __m1__ contains 7 __State__ instances and 7 instances of the __Transition__ metaclass.     However, since si<sub>3</sub>__ declared the importance of __State__ to be greater than the __Transition__ metaclass, the selection process has preferred __T1 ; T2__. In fact    , the other possible chains include T4, which is a mutation that does not define any rules matching __State__ elements.

## DSL

The available transformations in this project are:

 - T1  PathExp \rightarrow PetriNet
 - T2  PetriNet \rightarrow XML 
 - T3  PetriNet \rightarrow XML 
 - T4  It is the transformation resulting from a mutation of T1 where all the rule concerning the __State__ element in the __PathExp__ metamodel has been removed.

The available semantic importance model is: 

Metamodel importance __si<sub>3</sub>__

```
author "Rob";

declare importance EClass = 1;
declare importance EStructuralFeature = 1;

use metamodel "PathExp"{
    declare importance for State = 5 {}
}
.....
```
