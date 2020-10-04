# HIDDENTOOL <img src="figures/22-lime-512.png" width=32 />

The presented tool offered a first level of customization on the meta-metamodel level, specifying the weight of the information loss w.r.t.. the concept of a lost instance or a lost value set in a structural feature. This means that for every instance lost in the transformation process, the tool will consider the same weight. This is a flat management of the key concepts defined in the corresponding metamodels, where all the metaclasses and relation-ships formalizing the application domain are treated in the same manner. In this work we extend the definition of information loss by introducing the notion of semantic importance, already discussed in literature, to be expressed for the elements of the metamodels involved in the transformation.

We present 𝐻𝐼𝐷𝐷𝐸𝑁𝑇𝑂𝑂𝐿 + as an extension of the HIDDENTOOL tool. In particular, we provide the means to specify the semantic importance of metamodel elements and consider it to rank the possible transformation chains. In other words, 𝐻𝐼𝐷𝐷𝐸𝑁𝑇𝑂𝑂𝐿 + permits to rank transformation chains according
to the induced information loss, which is, in turn, weighted concerning the semantic importance of the involved metamodel elements. Figure 4 shows the main architecture of 𝐻𝐼𝐷𝐷𝐸𝑁𝑇𝑂𝑂𝐿 + . The user request consists of the input model that needs to be transformed, and the wanted target metamodel. The input model is also used to retrieve the corresponding metamodel, which can be enriched by the modeler to specify the semantic importance of the contained elements. A dedicated domain-specific language (DSL) is used to specify such importance, which is stored in a preference model for future reuse. The Chain Discoverer component is in charge of finding all the transformation chains that satisfy the user request. The available chains are ranked concerning the induced information loss, which is calculated by the Information Loss Calculator by relying on the Semantic Reasoner component. The ranked list is shown to the user, who can select and execute the preferred one. The Optimal Chain Selector and Chain Executor components are in charge of such two last phases.
The DSL that has been conceived for specifying the semantic

![Proposed Tool](figures/approach.png)
 <em>Proposed tool.</em>
 
## Running example

In this section we are going to explore a running example based on three metamodels, i.e. __ShapeMM1__, __ShapeMM2__ and __ShapeMM3__ part of a project where three transformations are possible, i.e. __T1, T2, T3__, as depicted in Figure below.

![Running example](figures/ex2-new.png)
<em>Semantic Importance-based Information Loss.</em>

## Execution on Real Examples

We show the results of executing the tool first with the project shown as running example, and then with other 4 projects, downloaded from the ATL Zoo.
[Table](#table1) reports the name of the project, the available chains if more than one available and the selected chain by the approach. Also the available transformations can be found on this github repository ([case study folder](tool/case_study)). The annotations in the metamodel elements are reported randomly in order to check the effectiveness of the approach.
The result is then reported in terms of IL if the approach is able to identify the best chain wrt. the input model and the semantically annotated metamodel on the input model.

<a id="table1"></a>
| Projects  |  Available chains |  Selected |  IL |
|  :---:       |:---:|:---:|:---:|
| [Shapes](wiki/shape.md)       | T1 --> T3 <hr/> T2 --> T3  | T2 --> T3  | 1.73 <hr/> **1.07**  |
| [Grafcet2PNML](wiki/grafcet.md) | Grafcet2PetriNet --> PetriNet2PNML v1.0 <hr/> Grafcet2PetriNet --> PetriNet2PNML v1.87 | Grafcet --> PetriNet --> PNML v1.0 | **0.40** <hr/> 2.40 |
| [Table2XML](wiki/table.md)    | Table2HTML --> HTML2XML v1.0 <hr/> Table2HTML --> HTML2XML v1.6  | Table2HTML --> HTML2XML v1.0  | **0** <hr/> 456  |
| [Ant2XML](wiki/ant.md)      | ANT2Maven --> Maven2XML v1.0 <hr/> ANT2Maven --> Maven2XML v1.01  | ANT2Maven --> Maven2XML v1.0 | **0** <hr/> 62.59  |
| [PathExp2XML](wiki/path.md)     | PathExp2PetriNet -> PetriNet2XML v0.6 <hr/> PathExp2PetriNet -> PetriNet2XML v0.9 | PathExp2PetriNet -> PetriNet2XML v0.9  | 3.0 <hr/> **0**  |

<em>Result of the execution on different projects.</em>

### Getting started with HIDDENTOOL+

HIDDENTOOL+ is provided as a maven project, which can be run within ECLIPSE IDE. 

  1. clone this repository and then point to the tool folder 
  ``` git clone https://github.com/MDEGroup/CITRICplus && cd CITRICplus/tool```
  2. install all maven dependencies
  ```mvn install```
  3. import the source code in ECLIPSE as an ```Existing maven project```
  4. use the provided launch files to execute different case studies as you prefer.
  
HIDDENTOOL+ computes transformation chaining at runtime, without generetating middle models. For example, to compute the best chain between T1 --> T3, and T2 --> T3 HIDDENTOOL+ does not build the model conforming to the ShapeMM2 metamodel.
Result of our case studies are listed in [Table 1](#table1).
