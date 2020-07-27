# Table2XML

## Chains description
 - Chain **Ch1**
   - Transformation [Table2HTML v1.0](../tool/case_study/Table2HTML2XML/v1.0/Table2HTML.atl): _Table2HTML_ maps the following metaclasses and structural features: 
       - Table!Table --> HTML!TABLE
          - Table!Table.rows --> HTML!trs 
       - Table!Row --> HTML!TR
          - Table!Row.cells --> HTML.TR.tds 
       - Table!Cell --> HTML!TH
          - Table!Cell.value --> HTML!TH.value 
       - Table!Cell --> HTML!TD 
          - Table!Cell.value --> HTML!TD.value 
          - "<left|center|rigth>" --> HTML!TD.align
   - Transformation [HTML2XML v1.0](../tool/case_study/Table2HTML2XML/v1.0/HTML2XML.atl): _HTML2XML_ maps metaclasses and StructuralFeature as follows:
       - HTML!HTML --> XML!Root
          - "html" --> XML!Root.name
          - HTML!HTML.head --> XML!Root.children
          - HTML!HTML.body --> XML!Root.children
       - HTML!HTMLElement --> XML!Element
          - HTML!HTMLElement.children --> XML!Element.children 
       - HTML!HEAD --> XML!Element
          - "head" --> XML!Element.name
          - HTML.HTMLHead.headElements --> XML!Element.children
       - HTML!TITLE --> XML!Element
          - 'title' --> XML!Element.name
          - HTML!Title.value --> XML.Element.children 
       - HTML!BODY --> XML!Element
          - "body" --> XML!Element.name
          - HTML!BODY.bodyElements --> children
       - HTML!TABLE --> XML!Element
          - "table" --> XML!Element.name
          - <HTML!Table.border| 0 > --> XML!Element.children 
          - HTML!Table.trs --> XML!Element.children
       - HTML!TR --> XML!Element
          - "tr" --> XML!Element.name
          - HTML!TR.tds --> children
       - HTML!TD --> XML!Element
          - "td" --> XML!Element.name
          - HTML!TD.value --> XML!Element.children
       - HTML!TH --> XML!Element
          - "th" --> XML!Element.name
  - Chain **Ch2**
    - Transformation [TABLE2HTML v1.6](Table2HTML2XML/v1.6/Table2HTML.atl): _TABLE2HTML_ maps the following metaclasses and structural features:
       - Table!Table --> HTML!TABLE
         - Table!Table.rows --> HTML!trs 
       - Table!Row --> HTML!TR
         - Table!Row.cells --> HTML.TR.tds 
       - Table!Cell --> HTML!TH
         - Table!Cell.value --> HTML!TH.value 
       - Table!Cell --> HTML!TD 
         - Table!Cell.value --> HTML!TD.value 
         - "<left|center|rigth>" --> HTML!TD.align
    - Transformation [HTML2XML v1.6](../tool/case_study/Table2HTML2XML/v1.6/HTML2XML.atl): _HTML2XML_ maps metaclasses and StructuralFeature as follows:
      - HTML!HTML --> XML!Root
        - "html" --> XML!Root.name  

## Input Model

The given input model ([Table2HTML.xmi](../tool/case_study/Table2HTML2XML/Table2HTML.xmi)) 

For sake of this example we have assigned to the  metaclasses a ```weight = ```, while the structural feature  has a ```weight = 1```.

## Chaining results

| Projects  |  Available chains |  Selected |  IL |
|  :---:       |:---:|:---:|:---:|
| [Table2XML](wiki/table.md)    | Table2HTML --> HTML2XML v1.0 <hr/> Table2HTML --> HTML2XML v1.6  | Table2HTML --> HTML2XML v1.6  | 7.6 <hr/> **3.1**  |

<em>Results of CITRIC+ over Ch1 and Ch2 chains.</em>
