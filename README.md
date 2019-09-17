# File Management System (FMS)

The FMS will be used for submitting all data files to AGR.
The rest endpoint: `http://fms.alliancegenome.org/api/data/submit` is using a multipart post.

Here is an example using curl:

```
curl \
	-H "Authorization: Bearer 2C07D715..." \
	-X POST "https://fms.alliancegenome.org/api/data/submit" \
	-F "ReleaseVersion_DataType_DataSubType=@/full/path/to/file1.json" \
	-F "ReleaseVersion_DataType_DataSubType=@/full/path/to/file2.json"
```

Valid values for ReleaseVersion, DataType, and DataSubType can be found in the examples below.

## Contents

- [Release Version](#release-version)
- [Data Type](#data-type)
- [Data SubType](#data-subtype)
- [Examples](#examples)
  * [ReleaseVersion DataType DataSubType String format](#releaseversion-datatype-datasubtype-string-format)
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

## Release Version

| Release Version |
| --- |
| 0.6.0 |
| 1.0.0 |
| 1.7.0 |
| 2.0.0 |
| 2.1.0 |
| 2.2.0 |
| 2.3.0 |
| 2.4.0 |
| etc... |

This will be the current main release of the Alliance.

## Data Type

| Data Type        | What it means                     | Schema Validation File  | Format | Data SubType Required | Validation Required |
| ---              | ---                               | ---                                                            | ---  | ---  | ---  |
| AGM              | Affected Genomic Model            | /ingest/affectedGenomicModel/affectedGenomicModelMetaData.json | json | true | true |
| BGI              | Basic Gene information            | /ingest/gene/geneMetaData.json                                 | json | true | true |
| DAF              | Disease Ontology Annotations File | /ingest/disease/diseaseMetaDataDefinition.json                 | json | true | true |
| ORTHO            | Orthology Information File        | /ingest/orthology/orthologyMetaData.json                       | json | true | true |
| ALLELE           | Allele Information File           | /ingest/allele/alleleMetaData.json                             | json | true | true |
| GENOTYPE         | Genotype Information File         | /genotype/genotypeMetaDataDefinition.json                      | json | true | true |
| PHENOTYPE        | Phenotype Information File        | /ingest/phenotype/phenotypeMetaDataDefinition.json             | json | true | true |
| EXPRESSION       | Expression Information File       | /ingest/expression/wildtypeExpressionMetaDataDefinition.json   | json | true | true |
| VARIATION        | Variation Information File        | /ingest/allele/variantMetaData.json                            | json | true | true |
| SQTR             | Sequence Targeting Reagent File   | /genotype/sequenceTargetingReagentMetaData.json                | json | true | true |
| TRANSCRIPT       | Transcript                        | /ingest/sequenceFeature/transcriptMetaData.json                | json | false | true |
| GAF              | Gene Ontology Annotations File    | - | gaf    | true  | false |
| EXPRESSIONATLAS  | Expression Atlas Information File | - | xml    | true  | false |
| FASTA            | Assembly fasta file               | - | fa     | true  | false |
| GFF              | Gene Feature File                 | - | gff    | true  | false |
| ONTOLOGY         | Ontology Information File         | - | obo    | true  | false |
| INTERACTION      | Consolidated Interactions File    | - | tar.gz | false | false |
| NONGENESEQUENCEFEATURE | Non gene, non transcript/exon sequence features | /ingest/sequenceFeature/nonGeneSequenceFeatureMetaData.json   | json | false | false |

## Data SubType

| Name | Description |
| --- | --- |
| FB    | Fly Base |
| HUMAN | Human Supplied by RGD |
| MGD   | Mouse Genome Database |
| RGD   | Rat Genome Database |
| SGD   | Saccharomyces Genome Database |
| WB    | Worm Base |
| ZFIN  | Zebrafish Information Network |

## Examples

### ReleaseVersion DataType DataSubType String format

Valid combinations for Release-DataType-DataSubType are as follows:

| Type | What does it mean? |
| --------------- | --- |
| ReleaseVersion\_DataType\_DataSubType | Validation will occur for BGI, Disease, Orthology, Allele, Genotype, Phenotype, Expression and not for GAF, GFF, and Ontology, all files will be stored under the Release Version Directory in S3. |
| DataType\_DataSubType | Validation will occur for BGI, Disease, Orthology, Allele, Genotype, Phenotype, Expression and not for GAF, GFF, and Ontology, the current release version will get looked up based on date, and the default schema will be used to validate the file. All files will be stored under the Release Version Directory in S3.
| ReleaseVersion-DataType | Invalid (Data Type not found for: ReleaseVersion) |

### Valid examples for submitting files

#### One file at a time

	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "1.2.0_GFF_MGD=@MGI_1.0.4_GFF.gff"
	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "1.7.0_Allele_MGD=@MGI_1.0.4_feature.json"
	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "2.0.0_BGI_FB=@FB_1.0.4_BGI.json"
	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "GAF_MGD=@gene_association_1.0.mgi.gaf"
	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "Allele_ZFIN=@ZFIN_1.0.4_feature.json"
	
#### Multiple files at a time

	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "2.2.0_BGI_FB=@FB_1.0.4_BGI.json" \
		-F "2.2.0_Allele_FB=@FB_1.0.4_feature.json" \
		-F "2.2.0_Disease_FB=@FB_1.0.4_disease.json" \
		-F "2.2.0_GFF_FB=@FB_1.0.4_GFF.gff"
		
	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "BGI_FB=@FB_1.0.4_BGI.json" \
		-F "Allele_FB=@FB_1.0.4_feature.json" \
		-F "Disease_FB=@FB_1.0.4_disease.json" \
		-F "GFF_FB=@FB_1.0.4_GFF.gff"	


## Return object

The responce object that is returned will be based on the files that were submitted.

### Success example

For the following command:

	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "2.2.0_BGI_FB=@FB_1.0.4_BGI.json" \
		-F "2.2.0_Allele_FB=@FB_1.0.4_feature.json" \
		-F "2.2.0_Disease_FB=@FB_1.0.4_disease.json" \
		-F "2.2.0_GFF_FB=@FB_1.0.4_GFF.gff"

<details>
<summary>View Response</summary>
<pre>
```{
	"fileStatus": {
		"2.2.0_BGI_FB":"success",
		"2.2.0_Disease_FB":"success",
		"2.2.0_GFF_FB":"success",
		"2.2.0_Allele_FB":"success"
	},
	"status":"success"
}```
</details>

### Failed example

For the following command (Missing API Access Token):

	> curl \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "2.2.0_BGI_MGD=@MGI_1.0.4_BGI.json" \
		-F "2.2.0_Allele_MGD=@MGI_1.0.4_feature.json" \
		-F "2.2.0_Disease_MGD=@MGI_1.0.4_disease.json" \
		-F "2.2.0_GFF_MGD=@MGI_1.0.4_GFF.gff" 

<details>
<summary>View Failure Response</summary>
<pre>
{
	"fileStatus": {
		"2.2.0_BGI_MGD":"Authentication Failure: Please check your api_access_token",
		"2.2.0_Allele_MGD":"Authentication Failure: Please check your api_access_token",
		"2.2.0_Disease_MGD":"Authentication Failure: Please check your api_access_token",
		"2.2.0_GFF_MGD":"Authentication Failure: Please check your api_access_token"
	},
	"status":"failed"
}</pre>
</details>

For the following command (Errors in BGI):

	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "2.2.0_BGI_MGD=@MGI_1.0.4_BGI.json" \
		-F "2.2.0_Allele_MGD=@MGI_1.0.4_feature.json" \
		-F "2.2.0_Disease_MGD=@MGI_1.0.4_disease.json" \
		-F "2.2.0_GFF_MGD=@MGI_1.0.4_GFF.gff" 

<details>
<summary>View Failure Response</summary>
<pre>
{
	"fileStatus": {
		"2.2.0_Allele_MGD":"success",
		"2.2.0_BGI_MGD":"string \"https://en.wikipedia.org/wiki/Cathepsin L2\" is not a valid URI",
		"2.2.0_Disease_10090":"success",
		"2.2.0_GFF_MGD":"Unable to complete multi-part upload. Individual part upload failed : Your socket connection to the server was not read from or written to within the timeout period. Idle connections will be closed. (Service: Amazon S3; Status Code: 400; Error Code: RequestTimeout; Request ID: 3ABBDFD90F0C4CAA)"
	},
	"status":"failed"
}</pre>
</details>
	
In a failed example only the files that failed need to be attempted again:

	> curl \
		-H "Authorization: Bearer 2C07D715..." \
		-X POST "https://fms.alliancegenome.org/api/data/submit" \
		-F "2.2.0_BGI_10090=@MGI_1.0.4_BGI.json" \
		-F "2.2.0_GFF_10090=@MGI_1.0.4_GFF.gff" 

<details>
<summary>View Success Response</summary>
<pre>
{
	"fileStatus": {
		"2.2.0_BGI_10090":"success",
		"2.2.0_GFF_10090":"success"
	},
	"status":"success"
}</pre>
</details>

## Loader

The loader will run against the snapshot and releases API's, using the API Access Token for the "take snapshot" endpoint. Links to download these files will be in the following format:

	https://s3.amazonaws.com/mod-datadumps/<path>

### Releases

The following command can be used to pull a list of releases from the system that are available:
	
	> curl "https://fms.alliancegenome.org/api/data/releases"

<details>
<summary>View Success Response</summary>
<pre>
{
    "1.4.0": 1523284823719,
    "1.0.0": 1523284848246,
    "1.3.0": 1523284837284
}</pre>
</details>

### Snap Shot

The following command, can be used to pull a specific SnapShot by release version, release version is required.

	> curl "https://fms.alliancegenome.org/api/data/releases/2.2.0"

<details>
<summary>View Success Response</summary>
<pre>
{
    "releaseVersion": "1.4.0",
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

This will take a snapshot of all the latest datafiles for each DataSubType by each DataType. 

	> curl -H "Authorization: Bearer 2C07D715..." \
	"https://fms.alliancegenome.org/api/snapshot/take/2.2.0"

<details>
<summary>View Success Response</summary>
<pre>
{
    "releaseVersion": "1.4.0",
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
