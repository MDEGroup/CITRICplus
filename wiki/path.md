# PathExp2XML

The path expression to Petri nets example describes a transformation from a path expression to a Petri net. This document provides an overview of the whole transformation sequence that enables to produce an XML Petri net representation (in the PNML format) from a textual definition of a path expression. Its also describes the reverse transformation sequence that enables to build a textual path expression representation from a XML definition of a Petri net (in the PNML format).

## Chains description

Due to the complexity of the following transformation, we refer to the ATL codes directly, to better understand mappings among metaclasses and structural features.

 - Chain **Ch1**
   - Transformation [PathExp2PetriNet v0.6](../tool/case_study/PathExp2PetriNet2XML/v0.6/PathExp2PetriNet.atl): please, take a look at this file for mappings among metaclasses and structural feature of PathExp and PetriNet metamodels.

   - Transformation [PetriNet2XML v0.6](../tool/case_study/PathExp2PetriNet2XML/v0.6/PetriNet2XML.atl): please, take a look at this file for mappings among metaclasses and structural features of PetriNet and XML metamodels.

  - Chain **Ch2**
    - Transformation [PathExp2PetriNet v0.9](../tool/case_study/PathExp2PetriNet2XML/v0.9/PathExp2PetriNet.atl): please, take a look at this file for mappings among metaclasses and structural features of PathExp and PetriNet metamodels.
    
    - Transformation [PetriNet2XML v0.9](../tool/case_study/PathExp2PetriNet2XML/v0.9/PetriNet2XML.atl): please, take a look at this file for mappings among metaclasses and structural features of PetriNet and XML metamodels.


## Input Model

The given input model is the ([PathExp2PetriNet.xmi](../tool/case_study/PathExp2PetriNet2XML/PathExp2PetriNet.xmi)).

For sake of this example we have assigned to the  metaclasses a ```weight = 1```, while has ```weight = 5```.

## Chaining results

| Projects  |  Available chains |  Selected |  IL |
|  :---:       |:---:|:---:|:---:|
| [PathExp2XML](wiki/path.md)     | PathExp2PetriNet -> PetriNet2XML v0.6 <hr/> PathExp2PetriNet -> PetriNet2XML v0.9 | PathExp2PetriNet -> PetriNet2XML v0.9  | 3.0 <hr/> **0**  |
