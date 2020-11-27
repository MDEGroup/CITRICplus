# CITRIC<sup>+</sup> <img src="figures/22-lime-512.png" width=32 />

The presented tool offered a first level of customization on the meta-metamodel level, specifying the weight of the information loss w.r.t.. the concept of a lost instance or a lost value set in a structural feature. This means that for every instance lost in the transformation process, the tool will consider the same weight. This is a flat management of the key concepts defined in the corresponding metamodels, where all the metaclasses and relation-ships formalizing the application domain are treated in the same manner. In this work we extend the definition of information loss by introducing the notion of semantic importance, already discussed in literature, to be expressed for the elements of the metamodels involved in the transformation.

We present TOOLNAME+ as an extension of the TOOLNAME tool. In particular, we provide the means to specify the semantic importance of metamodel elements and consider it to rank the possible transformation chains. In other words, TOOLNAME+ permits to rank transformation chains according
to the induced information loss, which is, in turn, weighted concerning the semantic importance of the involved metamodel elements. Figure 4 shows the main architecture of TOOLNAME+ . The user request consists of the input model that needs to be transformed, and the wanted target metamodel. The input model is also used to retrieve the corresponding metamodel, which can be enriched by the modeler to specify the semantic importance of the contained elements. A dedicated domain-specific language (DSL) is used to specify such importance, which is stored in a preference model for future reuse. The Chain Discoverer component is in charge of finding all the transformation chains that satisfy the user request. The available chains are ranked concerning the induced information loss, which is calculated by the Information Loss Calculator by relying on the Semantic Reasoner component. The ranked list is shown to the user, who can select and execute the preferred one. The Optimal Chain Selector and Chain Executor components are in charge of such two last phases.
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



|         Project         | Run |   Chain   | Importance Model |   IL   |       |       |
|:-----------------------:|:---:|:---------:|:----------------:|:------:|:-----:|:-----:|
|                         |     |           |                  |   m1   |   m2  |   m3  |
|[Shapes](wiki/shape.md)         |  1  |  T1 ; T3  |        si1       |  1.73  |  1.73 |  1.73 |
|                         |  1   |  T2 ; T3  |        si1       |  **1.07**  |  **1.07** |  **1.07** |
|                         |  2  |  T4 ; T3  |        si1       |   **0**   |   **0**   |   **0**   |
|                         |   2  |  T2 ; T3  |        si1       |  1.07  |  1.07 |  1.07 |
|                         |  3  |  T4 ; T3  |        si2       |    **0**   |   **0**   |   **0**   |
|                         |  3   |  T5 ; T6  |        si2       |  3.33  |  3.33 |  3.33 |
|   [PathExp2PetriNet2XML](wiki/path.md)  |  1  |  T1 ; T2  |      default     |    3   |  1.33 |   **0**   |
|                         |   1  |  T1 ; T3  |      default     |    **0**   |   **0**   |   0   |
|                         |  2  |  T1 ; T2  |      default     |    3   |  **1.33** |  **0**  |
|                         |  2   |  T4 ; T3  |      default     |  **2.33**  |  1.67 |  2.33 |
|                         |  3  |  T1 ; T2  |        si3       |    3   |  **1.33** |   **0**   |
|                         |  3   |  T4 ; T3  |        si3       |  11.67 |  8.33 | 11.67 |
|  [Grafcet2PetriNet2PNML](wiki/grafcet.md)  |  1  |  T1 ; T2  |      default     |   **0.4**  |  **1.2**  |   **2**   |
|                         |   1  |  T3 ; T2  |      default     |   2.4  |  4.8  |  7.2  |
|                         |  2  |  T1 ; T2  |        si4       |   **0.4**  |  **2.8**  |   16  |
|                         |  2   |  T3 ; T2  |      default     |   4.8  |  4.8  |  **7.2**  |
|                         |  3  |  T1 ; T2  |        si5       |   **0.4**  |  **9.6**  |   **16**  |
|                         |  4   |  T3 ; T2  |      default     |   4.8  |  11.2 |  16.8 |
|      [ANT2Maven2XML](wiki/ant.md)      |  1  |  T3 ; T2  |      default     |   **8.4**  |  **5.6**  |  **8.4**  |
|                         |   1  |  T1 ; T2  |      default     |  62,59 | 62.59 | 45.52 |
|                         |  2  |  T3 ; T2  |        si6       |  **13.2**  |  **8.8**  |  **13.2** |
|                         |  2   |  T1 ; T2  |      default     |  62,59 | 62,59 | 45,52 |
|                         |  3  |  T3 ; T2  |        si6       |  **13.2**  |  **8.8**  |  **13.2** |
|                         |  3   |  T1 ; T2  |        si7       |  75.1  |  75.1 | 54.62 |
|      [Table2HTML2XML](wiki/table.md)     |  1  |  T1 ; T3  |        si8       |   **22**   |   **22**  |   **22**  |
|                         |   1  |  T2 ; T3  |        si8       | 723.33 |  570  |  185  |
|                         |  2  |  T1 ; T3  |        si9       |  **51.33** | **51.33** | **51.33** |
|                         |  2   |  T2 ; T3  |        si9       |   434  |  342  |  111  |
|                         |  3  |  T1 ; T3  |       si10       | **73.33** | **73.33** | 73.33 |
|                         |  3   | T2 ; T3 |       si10       | 144.67 |  114  |   **37**  |

<em>Result of the execution on different projects.</em>
