# Grafcet2PNML

## Chains description
 - Chain **Ch1**
   - Transformation [Grafcet2PetriNet v1.0](../tool/case_study/Grafcet2PetriNet2PNML/v1.0/Grafcet2PetriNet.atl): _Grafcet2PetriNet_ maps the following metaclasses and structural features:
     - Grafecet!Grafecet --> PetriNet!PetriNet
       - Grafcet!Grafecet.location --> PetriNet!PetriNet.location
       - Grafcet!Grafecet.name --> PetriNet!PetriNet.name
       - Grafcet!Grafecet.elements --> PetriNet!PetriNet.elements
       - Grafcet!Grafecet.connections --> PetriNet!PetriNet.arcs
     - Grafcet!Step --> PetriNet!Place
       - Grafcet!Step.location --> PetriNet!Place.location
       - Grafcet!Step.name --> PetriNet!Place.name
       - Grafcet!Step.grafcet --> PetriNet!Place.net
       - Grafcet!Step.incomingConnections --> PetriNet!Place.incomingConnections
       - Grafcet!Step.outgoingConnections --> PetriNet!Place.outgoingConnections
     - Grafcet!Transition --> PetriNet!Transition
       - Grafcet!Transition.location --> PetriNet!Transition.location
       - Grafcet!Transition.name --> PetriNet!Transition.name
       - Grafcet!Transition.grafcetPetriNet!Transition.net
       - Grafcet!Transition.incomingConnections --> PetriNet!Transition.incomingArc
       - Grafcet!Transition.outgoingConnections --> PetriNet!Transition.outgoingArc
     - Grafcet!StepToTransition --> PetriNet!PlaceToTransition
       - Grafcet!StepToTransition.location --> PetriNet!Step.location
       - Grafcet!StepToTransition.name --> PetriNet!Step.name
       - Grafcet!StepToTransition.grafcet --> PetriNet!Step.net
       - Grafcet!StepToTransition."from" --> PetriNet!Step."from" 
       - Grafcet!StepToTransition."to" --> PetriNet!Step."to"
     - Grafcet!TransitionToStep --> PetriNet!TransitionToPlace
       - Grafcet!TransitionToStep.location --> PetriNet!Step.location
       - Grafcet!TransitionToStep.name --> PetriNet!Step.name
       - Grafcet!TransitionToStep.grafcet --> PetriNet!Step.net
       - Grafcet!TransitionToStep."from" --> PetriNet!Step."from" 
       - Grafcet!TransitionToStep."to" --> PetriNet!Step."to"
   - Transformation [PetriNet2PNML v1.0](../tool/case_study/Grafcet2PetriNet2PNML/v1.0/PetriNet2PNML.atl): _Grafcet2PetriNet_ maps metaclasses and StructuralFeature as follows:
     - PetriNet!PetriNet --> PNML!PNMLDocument
       - PetriNet!PetriNet.location --> PNML!PNMLDocument.location
       - PetriNet!PetriNet.uri --> PNML!PNMLDocument.xmlns
       - PetriNet!PetriNet.net --> PNML!PNMLDocument.nets
       - PetriNet!Place --> PNML!Place
     - PetriNet!Place.name --> PNML!Place.name
       - PetriNet!Place.name --> PNML!Place.id
       - PetriNet!Place.location --> PNML!Place.location
     - PetriNet!Transition --> PNML!Transition
       - PetriNet!Transition.name --> PNML!Transition.name
       - PetriNet!Transition.name --> PNML!Transition.id
       - PetriNet!Transition.location --> PNML!Transition.location
     - PetriNet!Arc --> PNML!Arc
       - PetriNet!Arc.name <- PNML!Arc.name
       - PetriNet!Arc.location --> PNML!Arc.location
       - PetriNet!Arc.name --> PNML!Arc.id
       - PetriNet!Arc."from" --> PNML!Arc.source
       - PetriNet!Arc."to" --> PNML!Arc.target
  - Chain **Ch2**
    - Transformation [Grafcet2PetriNet v1.87](../tool/case_study/Grafcet2PetriNet2PNML/v1.87/Grafcet2PetriNet.atl): _Grafcet2PetriNet_ maps the following metaclasses and structural features:
      - Grafecet!Grafecet --> PetriNet!PetriNet
        - Grafcet!Grafecet.location --> PetriNet!PetriNet.location
	- Grafcet!Grafecet.name --> PetriNet!PetriNet.name
	- Grafcet!Grafecet.elements --> PetriNet!PetriNet.elements
        - Grafcet!Grafecet.connections --> PetriNet!PetriNet.arcs
      - Grafcet!Step --> PetriNet!Place
        - Grafcet!Step.location --> PetriNet!Place.location
	- Grafcet!Step.name --> PetriNet!Place.name
	- Grafcet!Step.grafcet --> PetriNet!Place.net
	- Grafcet!Step.incomingConnections --> PetriNet!Place.incomingConnections
	- Grafcet!Step.outgoingConnections --> PetriNet!Place.outgoingConnections
      - Grafcet!Transition --> PetriNet!Transition
        - Grafcet!Transition.location --> PetriNet!Transition.location
	- Grafcet!Transition.name --> PetriNet!Transition.name
	- Grafcet!Transition.grafcetPetriNet!Transition.net
  	- Grafcet!Transition.incomingConnections --> PetriNet!Transition.incomingArc
	- Grafcet!Transition.outgoingConnections --> PetriNet!Transition.outgoingArc
      - Grafcet!StepToTransition --> PetriNet!PlaceToTransition
        - Grafcet!StepToTransition.location --> PetriNet!Step.location
	- Grafcet!StepToTransition.name --> PetriNet!Step.name
	- Grafcet!StepToTransition.grafcet --> PetriNet!Step.net
	- Grafcet!StepToTransition."from" --> PetriNet!Step."from" 
	- Grafcet!StepToTransition."to" --> PetriNet!Step."to"
      - Grafcet!TransitionToStep --> PetriNet!TransitionToPlace
        - Grafcet!TransitionToStep.location --> PetriNet!Step.location
	- Grafcet!TransitionToStep.name --> PetriNet!Step.name
	- Grafcet!TransitionToStep.grafcet --> PetriNet!Step.net
	- Grafcet!TransitionToStep."from" --> PetriNet!Step."from" 
	- Grafcet!TransitionToStep."to" --> PetriNet!Step."to"
    - Transformation [PetriNet2PNML v1.87](../tool/case_study/Grafcet2PetriNet2PNML/v1.87/PetriNet2PNML.atl): _Grafcet2PetriNet_ maps metaclasses and StructuralFeature as follows:
      - PetriNet!PetriNet --> PNML!PNMLDocument
        - PetriNet!PetriNet.location --> PNML!PNMLDocument.location
        - PetriNet!PetriNet.uri --> PNML!PNMLDocument.xmlns
        - PetriNet!PetriNet.net --> PNML!PNMLDocument.nets
      - PetriNet!Place --> PNML!Place
        - PetriNet!Place.name --> PNML!Place.name
	- PetriNet!Place.name --> PNML!Place.id
	- PetriNet!Place.location --> PNML!Place.location
      - PetriNet!Arc --> PNML!Arc
        - PetriNet!Arc.name <- PNML!Arc.name
	- PetriNet!Arc.location --> PNML!Arc.location
	- PetriNet!Arc.name --> PNML!Arc.id
	- PetriNet!Arc."from" --> PNML!Arc.source
	- PetriNet!Arc."to" --> PNML!Arc.target

## Input Model

The given input model ([Grafcet2PetriNet.xmi](../tool/case_study/Grafcet2PetriNet2PNML/Grafcet2PetriNet.xmi)) 

For sake of this example we have assigned to the Grafecet!Step, Grafecet!Transition, Grafecet!Connection, Grafecet!StepToTransition, Grafecet!TransitionToStep, PetriNet!Place, PetriNet!Transition, PetriNet!Arc, PetriNet!PlateToTransition, and PetriNet!TransitionToPlace metaclasses a ```weight = 1```, while the  structural feature Grafecet!Transition.condition has a ```weight = 1```.

## Chaining results

| Projects  |  Available chains |  Selected |  IL |
|  :---:       |:---:|:---:|:---:|
| [Grafcet2PNML](../tool/case_study/Grafcet2PetriNet2PNML/) | Grafcet2PetriNet --> PetriNet2PNML v1.0 <hr/> Grafcet2PetriNet --> PetriNet2PNML v1.87 | Grafcet --> PetriNet --> PNML  | 1.23 <hr/> **0.63** |

<em>Results of CITRIC+ over Ch1 and Ch2 chains.</em>
