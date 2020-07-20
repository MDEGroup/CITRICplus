# Shapes

## Chains description
   - Chain Ch1
      - Transformation T1 = ShapeMM1 --> ShapeMM2
      - Transformation T3 = ShapeMM2 --> ShapeMM3
   - Chain Ch2
      - Transformation T2 = ShapeMM1 --> ShapeMM2
      - Transformation T3 = ShapeMM2 --> ShapeMM3

   - Chain 1 --> T1 maps the ShapeMM1!Circle metaclass with the Shape2MM!BlueCircle. In addition, T1 also maps the StructuralFeature name of the ShapeMM1 with the same feature of ShapeMM2.
   - Chain 1 --> T3 maps the ShapeMM2!BlueCircle with the ShapeMM3!Triangle and ShapeMM2!Diamond with ShapeMM3!Pentagon. In addition, T2 also maps the StructuralFeature name of the ShapeMM2 with the same feature of ShapeMM3.
  
   - Chain 2 --> T2 maps the ShapeMM1!Square metaclass with the Shape2MM!Diamond. In addition, T1 also maps the StructuralFeature name of the ShapeMM1 with the same feature of ShapeMM2.
   - Chain 2 --> T3 maps the ShapeMM2!BlueCircle with the ShapeMM3!Triangle and ShapeMM2!Diamond with ShapeMM3!Pentagon. In addition, T2 also maps the StructuralFeature name of the ShapeMM2 with the same feature of ShapeMM3.
   
## Input Model

The given input model has two instances of the ShapeMM1!Circle, and two instances of ShapeMM1!Square. Both the instances have a StructuralFeature name.
For sake of this example we have assigned to the ShapeMM1!Circle metaclass a ```weight = 2```, while the ShapeMM1!Square has a ```weight = 1```. We have also assigned a ```weight = 1``` to the _name_ structural feature.
Concerning the ShapeMM2, we have decided to assign a ```weight = 1``` for both the metaclasses as well as to the structural feature _name_.

## Chaining results

| Projects  |  Available chains |  Selected |  IL |
|  :---:       |:---:|:---:|:---:|
| [Shapes](wiki/shape.md)       | T1 --> T3 <hr/> T2 --> T3  | T2 --> T3  | 2.13 <hr/> **1.87**  |

<em>Results of CITRIC+ over Ch1 and Ch2 chains.</em>
