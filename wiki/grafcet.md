# Grafcet2PNML

## Chains description
   - Chain **Ch1**
      - Transformation _[Grafcet2PetriNet --> PetriNet2PNML v1.0](tool/case_study/Grafcet2PetriNet2PNML/Grafcet2PetriNet.atl)_: _Grafcet2PetriNet_ maps the TODO .... In addition, _Grafcet2PetriNet_ also maps the StructuralFeature TODO ..... 
      - Transformation _[PetriNet2PNML](tool/case_study/Grafcet2PetriNet2PNML/PetriNet2Grafcet.atl) (ShapeMM2 --> ShapeMM3)_: _Grafcet2PetriNet2PNML_ _Grafcet2PetriNet_ also maps the StructuralFeature TODO .....
   - Chain **Ch2**
      - Transformation _[T2]() (ShapeMM1 --> ShapeMM2)_: T2 maps the ShapeMM1!Square metaclass with the Shape2MM!Diamond. In addition, T1 also maps the StructuralFeature name of the ShapeMM1 with the same feature of ShapeMM2.
      - Transformation _[T3]() (ShapeMM2 --> ShapeMM3)_: T3 maps the ShapeMM2!BlueCircle with the ShapeMM3!Triangle and ShapeMM2!Diamond with ShapeMM3!Pentagon. In addition, T2 also maps the StructuralFeature name of the ShapeMM2 with the same feature of ShapeMM3.

## Input Model

The given input model ([shape1.xmi](tool/case_study/shapes/shape.xmi)) has two instances of the ShapeMM1!Circle, and two instances of ShapeMM1!Square. Both the instances have a StructuralFeature name.
For sake of this example we have assigned to the ShapeMM1!Circle metaclass a ```weight = 2```, while the ShapeMM1!Square has a ```weight = 1```. We have also assigned a ```weight = 1``` to the _name_ structural feature.
Concerning the ShapeMM2, we have decided to assign a ```weight = 1``` to both the metaclasses as well as to the structural feature _name_.

## Chaining results

| Projects  |  Available chains |  Selected |  IL |
|  :---:       |:---:|:---:|:---:|
| [Grafcet2PNML](tool/case_study/Grafcet2PetriNet2PNML/) | Grafcet2PetriNet --> PetriNet2PNML v1.0 <hr/> Grafcet2PetriNet --> PetriNet2PNML v1.87 | Grafcet --> PetriNet --> PNML  | 1.23 <hr/> **0.63** |

<em>Results of CITRIC+ over Ch1 and Ch2 chains.</em>
