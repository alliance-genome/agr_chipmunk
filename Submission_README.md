# Submission System

The submission system will be used for submitting all data files to AGR.
The rest endpoint: `http://www.alliancegenome.org/api/data/submit` is using a multipart post.

Here is an example using curl:

```
curl \
	-H "api_access_token: 2C07D715..." \
	-X POST "https://www.alliancegenome.org/api/data/submit" \
	-F "SchemaVersion_DataType_TaxonId=@/full/path/to/file1.json" \
	-F "SchemaVersion_DataType_TaxonId=@/full/path/to/file2.json"
```

Valid values for SchemaVersion, DataType, and TaxonId can be found in the examples below.

## Contents

- [Schema Version](#schema-version)
- [Data Type](#data-type)
- [Data SubType](#data-subtype)
- [Examples](#examples)
  * [SchemaVersion DataType TaxonId String format](#schemaversion-datatype-taxonid-string-format)
  * [Valid examples for submitting files](#valid-examples-for-submitting-files)
- [Return object](#return-object)
  * [Success example](#success-example)
  * [Failed example](#failed-example)
- [Loader](#loader)
  * [Releases](#releases)
  * [Snap Shot](#snap-shot)
  * [Take Snap Shot](#take-snap-shot)

## API Access Token

This will be a key that is generated for the DQM's to use for uploading files.

## Schema Version

| Schema Version |
| --- |
| 0.6.0.0 |
| 0.6.1.0 |
| 0.7.0.0 |
| 1.0.0.0 |
| 1.0.0.1 |
| 1.0.0.2 |
| 1.0.0.3 |
| 1.0.0.4 |
| 1.0.0.5 |
| 1.0.0.6 |
| etc... |

This will be the current release of the schema can be found in the [releases](https://github.com/alliance-genome/agr_schemas/releases) section for the schema repository. Schema does not follow the same release schedule as the main branches.

## Data Type

| Data Type | What it means | Schema Validation File | Format | Data SubType Required | Validation Required |
| --- | --- | --- | --- | --- | --- | --- |
| BGI          | Basic Gene information            | /gene/geneMetaData.json                                   | json | true | true |
| Disease      | Disease Ontology Annotations File | /disease/diseaseMetaDataDefinition.json                   | json | true | true |
| Orthology    | Orthology Information File        | /orthology/orthologyMetaData.json                         | json | true | true |
| Allele       | Allele Information File           | /allele/alleleMetaData.json                               | json | true | true |
| Genotype     | Genotype Information File         | /genotype/genotypeMetaDataDefinition.json                 | json | true | true |
| Phenotype    | Phenotype Information File        | /phenotype/phenotypeMetaDataDefinition.json               | json | true | true |
| Expression   | Expression Information File       | /expression/wildtypeExpressionMetaDataDefinition.json     | json | true | true |
| GAF          | Gene Ontology Annotations File    | - | tar.gz | true  | false |
| GFF          | Gene Feature File                 | - | gff    | true  | false |
| Ontology     | Ontology Information File         | - | obo    | true  | false |
| Interactions | Consolidated Interactions File    | - | tar.gz | false | false |

## Data SubType

| Name | Description |
| --- | --- |
| FB    | Fly Base |
| Human | Human Supplied by RGD |
| MGD   | Mouse Genome Database |
| RGD   | Rat Genome Database |
| SGD   | Saccharomyces Genome Database |
| WB    | Worm Base |
| ZFIN  | Zebrafish Information Network |

## Examples

### SchemaVersion DataType TaxonId String format

Valid combinations for Schema-DataType-TaxonId are as follows:

| Type | What does it mean? |
| --------------- | --- |
| SchemaVersion\_DataType\_DataSubType | Validation will occur for BGI, Disease, Orthology, Allele, Genotype, Phenotype, Expression and not for GAF, GFF, and Ontology, all files will be stored under the Schema Directory in S3. |
| DataType\_DataSubType | Validation will occur for BGI, Disease, Orthology, Allele, Genotype, Phenotype, Expression and not for GAF, GFF, and Ontology, the current schema version will get looked up from Github. All files will be stored under the Schema Directory in S3.
| SchemaVersion-DataType | Invalid (Data Type not found for: SchemaVersion) |

### Valid examples for submitting files

#### One file at a time

	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "0.7.0.0_GFF_MGD=@MGI_1.0.4_GFF.gff"
	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "0.6.2.0_Allele_MGD=@MGI_1.0.4_feature.json"
	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "0.6.1.0_BGI_FB=@FB_1.0.4_BGI.json"
	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "GAF_MGD=@gene_association_1.0.mgi.gaf"
	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "Allele_ZFIN=@ZFIN_1.0.4_feature.json"
	
#### Multiple files at a time

	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "0.7.0.0_BGI_FB=@FB_1.0.4_BGI.json" \
		-F "0.7.0.0_Allele_FB=@FB_1.0.4_feature.json" \
		-F "0.7.0.0_Disease_FB=@FB_1.0.4_disease.json" \
		-F "0.7.0.0_GFF_FB=@FB_1.0.4_GFF.gff"
		
	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "BGI_FB=@FB_1.0.4_BGI.json" \
		-F "Allele_FB=@FB_1.0.4_feature.json" \
		-F "Disease_FB=@FB_1.0.4_disease.json" \
		-F "GFF_FB=@FB_1.0.4_GFF.gff"	


## Return object

The responce object that is returned will be based on the files that were submitted.

### Success example

For the following command:

	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "0.7.0.0_BGI_FB=@FB_1.0.4_BGI.json" \
		-F "0.7.0.0_Allele_FB=@FB_1.0.4_feature.json" \
		-F "0.7.0.0_Disease_FB=@FB_1.0.4_disease.json" \
		-F "0.7.0.0_GFF_FB=@FB_1.0.4_GFF.gff"

<details>
<summary>View Response</summary>
<pre>
```{
	"fileStatus": {
		"0.7.0.0_BGI_FB":"success",
		"0.7.0.0_Disease_FB":"success",
		"0.7.0.0_GFF_FB":"success",
		"0.7.0.0_Allele_FB":"success"
	},
	"status":"success"
}```
</details>

### Failed example

For the following command (Missing API Access Token):

	> curl \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "0.7.0.0_BGI_MGD=@MGI_1.0.4_BGI.json" \
		-F "0.7.0.0_Allele_MGD=@MGI_1.0.4_feature.json" \
		-F "0.7.0.0_Disease_MGD=@MGI_1.0.4_disease.json" \
		-F "0.7.0.0_GFF_MGD=@MGI_1.0.4_GFF.gff" 

<details>
<summary>View Failure Response</summary>
<pre>
{
	"fileStatus": {
		"0.7.0.0_BGI_MGD":"Authentication Failure: Please check your api_access_token",
		"0.7.0.0_Allele_MGD":"Authentication Failure: Please check your api_access_token",
		"0.7.0.0_Disease_MGD":"Authentication Failure: Please check your api_access_token",
		"0.7.0.0_GFF_MGD":"Authentication Failure: Please check your api_access_token"
	},
	"status":"failed"
}</pre>
</details>

For the following command (Errors in BGI):

	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "0.7.0.0_BGI_MGD=@MGI_1.0.4_BGI.json" \
		-F "0.7.0.0_Allele_MGD=@MGI_1.0.4_feature.json" \
		-F "0.7.0.0_Disease_MGD=@MGI_1.0.4_disease.json" \
		-F "0.7.0.0_GFF_MGD=@MGI_1.0.4_GFF.gff" 

<details>
<summary>View Failure Response</summary>
<pre>
{
	"fileStatus": {
		"0.7.0.0_Allele_MGD":"success",
		"0.7.0.0_BGI_MGD":"string \"https://en.wikipedia.org/wiki/Cathepsin L2\" is not a valid URI",
		"0.7.0.0_Disease_10090":"success",
		"0.7.0.0_GFF_MGD":"Unable to complete multi-part upload. Individual part upload failed : Your socket connection to the server was not read from or written to within the timeout period. Idle connections will be closed. (Service: Amazon S3; Status Code: 400; Error Code: RequestTimeout; Request ID: 3ABBDFD90F0C4CAA)"
	},
	"status":"failed"
}</pre>
</details>
	
In a failed example only the files that failed need to be attempted again:

	> curl \
		-H "api_access_token: 2C07D715..." \
		-X POST "https://www.alliancegenome.org/api/data/submit" \
		-F "0.7.0.0_BGI_10090=@MGI_1.0.4_BGI.json" \
		-F "0.7.0.0_GFF_10090=@MGI_1.0.4_GFF.gff" 

<details>
<summary>View Success Response</summary>
<pre>
{
	"fileStatus": {
		"0.7.0.0_BGI_10090":"success",
		"0.7.0.0_GFF_10090":"success"
	},
	"status":"success"
}</pre>
</details>

## Loader

The loader will run against the snapshot and releases API's, using the API Access Token for the "take snapshot" endpoint. One extra optional parameter is "system" which desinates the pipeline that will be used for releasing data. If the parameter is omited then it will be assumed value of "production". Links to download these files will be in the following format:

	https://s3.amazonaws.com/mod-datadumps/<path>

### Releases

The following command can be used to pull a list of releases from the system that are available:
	
	> curl "https://www.alliancegenome.org/api/data/releases"

<details>
<summary>View Success Response</summary>
<pre>
{
    "1.4.0.0": 1523284823719,
    "1.0.0.0": 1523284848246,
    "1.3.0.0": 1523284837284
}</pre>
</details>

### Snap Shot

The following command, can be used to pull a specific SnapShot by release version, release version is required.

	> curl "https://www.alliancegenome.org/api/data/snapshot?releaseVersion=1.4.0.0"

<details>
<summary>View Success Response</summary>
<pre>
{
    "releaseVersion": "1.4.0.0",
    "schemaVersion": "1.0.0.2",
    "snapShotDate": 1523284823719,
    "dataFiles": [
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "Allele",
            "dataSubType": "MGD",
            "s3path": "1.0.0.2/Allele/MGD/1.0.0.2\_Allele\_MGD\_0.json",
            "uploadDate": 1522181792721
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "Disease",
            "dataSubType": "MGD",
            "s3path": "1.0.0.2/Disease/MGD/1.0.0.2\_Disease\_MGD\_0.json",
            "uploadDate": 1522181816273
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "Allele",
            "dataSubType": "ZFIN",
            "path": "1.0.0.2/Allele/ZFIN/1.0.0.2\_Allele\_ZFIN\_2.json",
            "uploadDate": 1522179715428
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "BGI",
            "dataSubType": "ZFIN",
            "s3path": "1.0.0.2/BGI/ZFIN/1.0.0.2\_BGI\_ZFIN\_1.json",
            "uploadDate": 1522181715592
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "GFF",
            "dataSubType": "ZFIN",
            "s3path": "1.0.0.2/GFF/ZFIN/1.0.0.2\_GFF\_ZFIN\_1.gff",
            "uploadDate": 1522181475376
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "Disease",
            "dataSubType": "ZFIN",
            "s3path": "1.0.0.2/Disease/ZFIN/1.0.0.2\_Disease\_ZFIN\_1.json",
            "uploadDate": 1522180298184
        }
    ]
}</pre>
</details>

### Take Snap Shot

This will take a snapshot of all the latest datafiles for each Taxon Id by each DataType. 

	> curl -H "api_access_token: 2C07D715..." \
	"https://www.alliancegenome.org/api/data/takesnapshot?releaseVersion=1.4.0.0"

<details>
<summary>View Success Response</summary>
<pre>
{
    "releaseVersion": "1.4.0.0",
    "schemaVersion": "1.0.0.2",
    "snapShotDate": 1523284823719,
    "dataFiles": [
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "Allele",
            "dataSubType": "MGD",
            "s3path": "1.0.0.2/Allele/MGD/1.0.0.2\_Allele\_MGD\_0.json",
            "uploadDate": 1522181792721
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "Disease",
            "dataSubType": "MGD",
            "s3path": "1.0.0.2/Disease/MGD/1.0.0.2\_Disease\_MGD\_0.json",
            "uploadDate": 1522181816273
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "Allele",
            "dataSubType": "ZFIN",
            "path": "1.0.0.2/Allele/ZFIN/1.0.0.2\_Allele\_ZFIN\_2.json",
            "uploadDate": 1522179715428
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "BGI",
            "dataSubType": "ZFIN",
            "s3path": "1.0.0.2/BGI/ZFIN/1.0.0.2\_BGI\_ZFIN\_1.json",
            "uploadDate": 1522181715592
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "GFF",
            "dataSubType": "ZFIN",
            "s3path": "1.0.0.2/GFF/ZFIN/1.0.0.2\_GFF\_ZFIN\_1.gff",
            "uploadDate": 1522181475376
        },
        {
            "schemaVersion": "1.0.0.2",
            "dataType": "Disease",
            "dataSubType": "ZFIN",
            "s3path": "1.0.0.2/Disease/ZFIN/1.0.0.2\_Disease\_ZFIN\_1.json",
            "uploadDate": 1522180298184
        }
    ]
}</pre>
</details>