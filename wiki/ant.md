# Ant2XML
Ant is an open source build tool (a tool dedicated to the assembly of the different pieces of a program) from the Apache Software Foundation. Ant is the most commonly used build tool for Java programs. Maven is another build tool created by the Apache Software Foundation. It is an extension of Ant because ant Tasks can be used in Maven. The difference from Ant is that a project can be reusable. This example describes a transformation from an Ant file to Maven files (project.xml and maven.xml).

## Chains description
Due to the transformation complexy we demand to the ATL code directly to better understand mapping among Metaclasses and structural features.

 - Chain **Ch1**
   - Transformation [Table2HTML v1.0](../tool/case_study/Table2HTML2XML/v1.0/Table2HTML.atl): _Table2HTML_ maps the following metaclasses and structural features: 

   - Transformation [HTML2XML v1.0](../tool/case_study/Table2HTML2XML/v1.0/HTML2XML.atl): _HTML2XML_ maps metaclasses and StructuralFeature as follows:

  - Chain **Ch2**
    - Transformation [TABLE2HTML v1.6](Table2HTML2XML/v1.6/Table2HTML.atl): _TABLE2HTML_ maps the following metaclasses and structural features:
    
    - Transformation [HTML2XML v1.6](../tool/case_study/Table2HTML2XML/v1.6/HTML2XML.atl): _HTML2XML_ maps metaclasses and StructuralFeature as follows:


## Input Model

The given input model is the ([Ant2Maven.xmi](../tool/case_study/Ant2Maven2XML/Ant2Maven.xmi)).

For sake of this example we have assigned to the  metaclasses a ```weight = 1```, while has ```weight = 5```.

## Chaining results

| Projects  |  Available chains |  Selected |  IL |
|  :---:       |:---:|:---:|:---:|
| [Ant2XML](wiki/ant.md)      | ANT2Maven --> Maven2XML v1.0 <hr/> ANT2Maven --> Maven2XML v2  | ANT2Maven --> Maven2XML  | **0** <hr/> 5.92  |

<em>Results of CITRIC+ over Ch1 and Ch2 chains.</em>
