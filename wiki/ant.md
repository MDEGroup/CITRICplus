# Ant2XML
Ant is an open source build tool (a tool dedicated to the assembly of the different pieces of a program) from the Apache Software Foundation. Ant is the most commonly used build tool for Java programs. Maven is another build tool created by the Apache Software Foundation. It is an extension of Ant because ant Tasks can be used in Maven. The difference from Ant is that a project can be reusable. This example describes a transformation from an Ant file to Maven files (project.xml and maven.xml).

## Chains description
Due to the complexity of the following transformation, we refer to the ATL codes directly, to better understand mappings among metaclasses and structural features.

 - Chain **Ch1**
   - Transformation [Ant2Maven v1.0](../tool/case_study/Ant2Maven2XML/v1.0/Ant2Maven.atl): please, take a look at this file for mappings among metaclasses and structural feature of Ant and Maven metamodels.

   - Transformation [Maven2XML v1.0](../tool/case_study/Ant2Meven2XML/v1.0/Maven2XML.atl): please, take a look at this file for mappings among metaclasses and structural features of Maven and XML metamodels.

  - Chain **Ch2**
    - Transformation [Ant2Meven v1.01](../tool/case_study/Ant2Maven2XML/v1.01/Ant2Maven.atl): please, take a look at this file for mappings among metaclasses and structural features of Ant and Maven metamodels.
    
    - Transformation [Maven2XML v1.01](../tool/case_study/Ant2Maven2XML/v1.01/Maven2XML.atl): please, take a look at this file for mappings among metaclasses and structural features of Maven and XML metamodels.


## Input Model

The given input model is the ([Ant2Maven.xmi](../tool/case_study/Ant2Maven2XML/Ant2Maven.xmi)).

## Chaining results

| Projects  |  Available chains |  Selected |  IL |
|  :---:       |:---:|:---:|:---:|
| [Ant2XML](wiki/ant.md)      | ANT2Maven --> Maven2XML v1.0 <hr/> ANT2Maven --> Maven2XML v2  | ANT2Maven --> Maven2XML  | **0** <hr/> 5.92  |

<em>Results of CITRIC+ over Ch1 and Ch2 chains.</em>
