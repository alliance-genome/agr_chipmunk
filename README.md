# AGR Chipmunk - File Management System (FMS)

AGR Chipmunk is the Alliance of Genome Resources' File Management System (FMS), providing a centralized REST API for data file submissions, validation, and distribution across multiple model organism databases (MODs). Built with Quarkus and React, it serves as the primary data ingestion pipeline for the Alliance.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Development](#development)
- [API Documentation](#api-documentation)
- [Data Types and Submission](#data-types-and-submission)
- [Deployment](#deployment)
- [Configuration](#configuration)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Overview

The FMS enables data providers from various model organism databases to submit, validate, and manage genomic data files. It provides:

- **Multipart file upload API** for bulk data submission
- **JSON schema validation** for structured data types
- **AWS S3 integration** for scalable file storage
- **Release versioning** and snapshot management
- **Authentication** via Bearer tokens
- **Web UI** for monitoring and administration

### Supported Model Organism Databases

- **FB** - FlyBase
- **MGD** - Mouse Genome Database  
- **RGD** - Rat Genome Database
- **SGD** - Saccharomyces Genome Database
- **WB** - WormBase
- **ZFIN** - Zebrafish Information Network
- **HUMAN** - Human data supplied by RGD

## Architecture

The application follows a three-tier architecture:

```
┌─────────────────────────────────────────────────────────┐
│                    Frontend (React)                      │
│                 src/main/cliapp/                         │
├─────────────────────────────────────────────────────────┤
│                  Backend API (Quarkus)                   │
│           src/main/java/.../agr_submission/              │
├─────────────────────────────────────────────────────────┤
│                    Data Layer                            │
│         PostgreSQL Database    |    AWS S3 Storage       │
└─────────────────────────────────────────────────────────┘
```

### Technology Stack

- **Backend**: Java 21, Quarkus framework
- **Frontend**: React 16, Redux, Bootstrap 4
- **Database**: PostgreSQL with Hibernate ORM
- **Storage**: AWS S3 for file storage
- **Build Tools**: Maven (backend), npm/yarn (frontend)
- **Container**: Docker with multi-stage builds
- **Deployment**: AWS Elastic Beanstalk

## Features

### Core Functionality

- **Multi-file Upload**: Submit multiple data files in a single request
- **Schema Validation**: Automatic validation against JSON schemas for supported data types
- **Version Management**: Track data by release versions
- **Snapshot System**: Create and retrieve point-in-time data snapshots
- **Download Service**: Stable URLs for data file retrieval
- **Audit Logging**: Track all API requests and data modifications

### Supported Data Types

| Data Type | Description | Format | Validation |
|-----------|-------------|--------|------------|
| BGI | Basic Gene Information | JSON | Required |
| AGM | Affected Genomic Model | JSON | Required |
| ALLELE | Allele Information | JSON | Required |
| DAF | Disease Annotations | JSON | Required |
| ORTHO | Orthology Information | JSON | Required |
| GENOTYPE | Genotype Information | JSON | Required |
| PHENOTYPE | Phenotype Information | JSON | Required |
| EXPRESSION | Expression Data | JSON | Required |
| VARIATION | Variation Information | JSON | Required |
| SQTR | Sequence Targeting Reagent | JSON | Required |
| TRANSCRIPT | Transcript Data | JSON | Required |
| GAF | Gene Ontology Annotations | GAF | Not Required |
| GFF | Gene Feature File | GFF | Not Required |
| FASTA | Assembly Sequences | FA | Not Required |
| ONTOLOGY | Ontology Files | OBO | Not Required |
| INTERACTION | Interaction Data | TAR.GZ | Not Required |

## Prerequisites

- Java 21 or higher
- Maven 3.9.9 or higher
- Node.js 12+ and npm
- PostgreSQL 12+
- Docker (optional, for containerized deployment)
- AWS CLI (for deployment)

## Installation

### Quick Start with Docker

```bash
# Clone the repository
git clone https://github.com/alliance-genome/agr_chipmunk.git
cd agr_chipmunk

# Build and run with Docker
make docker
make docker-run
```

The application will be available at http://localhost:8080

### Manual Installation

1. **Clone the repository**:
```bash
git clone https://github.com/alliance-genome/agr_chipmunk.git
cd agr_chipmunk
```

2. **Set up the database**:
```bash
# Create PostgreSQL database
createdb chipmunk
```

3. **Configure application properties**:
```bash
cp src/main/resources/application.properties.defaults src/main/resources/application.properties
# Edit application.properties with your database and AWS credentials
```

4. **Build the frontend**:
```bash
cd src/main/cliapp
npm install
npm run build
cd ../../..
```

5. **Build the backend**:
```bash
mvn clean package
```

6. **Run the application**:
```bash
java -jar target/agr_chipmunk-runner.jar
```

## Development

### Project Structure

```
agr_chipmunk/
├── src/
│   ├── main/
│   │   ├── java/                 # Backend Java code
│   │   │   └── org/alliancegenome/agr_submission/
│   │   │       ├── controllers/  # REST endpoints
│   │   │       ├── services/     # Business logic
│   │   │       ├── dao/          # Data access layer
│   │   │       ├── entities/     # JPA entities
│   │   │       └── auth/         # Authentication
│   │   ├── cliapp/               # Frontend React application
│   │   │   ├── src/
│   │   │   │   ├── components/  # React components
│   │   │   │   ├── containers/  # Container components
│   │   │   │   ├── actions/     # Redux actions
│   │   │   │   └── reducers/    # Redux reducers
│   │   │   └── package.json
│   │   └── resources/            # Configuration files
│   └── test/                     # Test files
├── pom.xml                       # Maven configuration
├── Dockerfile                    # Container definition
├── docker-compose.yml           # Docker Compose setup
└── Makefile                     # Build automation
```

### Development Mode

Run the backend in development mode with hot reload:

```bash
# Backend with hot reload
mvn compile quarkus:dev

# Frontend with hot reload (in separate terminal)
cd src/main/cliapp
npm start
```

The backend API will be available at http://localhost:8080 and the frontend development server at http://localhost:3000.

### Setting Up Your Development Environment

1. **IDE Setup**:
   - **IntelliJ IDEA**: Import as Maven project, ensure Java 21 SDK is configured
   - **VS Code**: Install Java Extension Pack and Quarkus extension

2. **Database Setup**:
```bash
# Start PostgreSQL with Docker
docker run -d --name chipmunk-db \
  -e POSTGRES_DB=chipmunk \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:12
```

3. **Environment Variables**:
Create a `.env` file in the project root:
```properties
AWS_ACCESS_KEY=your_access_key
AWS_SECRET_KEY=your_secret_key
AWS_BUCKET_NAME=mod-datadumps-dev
QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://localhost:5432/chipmunk
QUARKUS_DATASOURCE_USERNAME=postgres
QUARKUS_DATASOURCE_PASSWORD=postgres
```

### Code Style and Standards

- **Java**: Follow standard Java conventions, use meaningful variable names
- **React**: Use functional components with hooks where possible
- **Git**: Use conventional commit messages (feat:, fix:, docs:, etc.)

### Making Changes

1. **Backend Changes**:
   - Controllers are in `src/main/java/.../controllers/`
   - Services contain business logic in `src/main/java/.../services/`
   - Entities define the data model in `src/main/java/.../entities/`

2. **Frontend Changes**:
   - Components are in `src/main/cliapp/src/components/`
   - Redux state management in `src/main/cliapp/src/actions/` and `reducers/`
   - API calls are made through axios in the actions

3. **Adding a New Data Type**:
   - Add the type to `DataType` entity
   - Create validation schema in resources
   - Update `SubmissionService` to handle the new type
   - Add UI support if needed

### Debugging

1. **Backend Debugging**:
```bash
# Run with debug port
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5045 \
  -jar target/agr_chipmunk-runner.jar
```

2. **Frontend Debugging**:
   - Use React Developer Tools browser extension
   - Redux DevTools for state debugging

3. **Database Queries**:
   - Enable SQL logging in application.properties:
   ```properties
   %dev.quarkus.log.category."org.hibernate.SQL".level=DEBUG
   ```

## API Documentation

### OpenAPI/Swagger Documentation

The API documentation is available at:
- Development: http://localhost:8080/q/swagger-ui
- Production: https://fms.alliancegenome.org/q/swagger-ui

### Main Endpoints

#### Submit Data Files
```bash
POST /api/data/submit
Authorization: Bearer <token>
Content-Type: multipart/form-data

curl -H "Authorization: Bearer YOUR_TOKEN" \
  -X POST "https://fms.alliancegenome.org/api/data/submit" \
  -F "3.2.0_BGI_MGD=@mouse_genes.json" \
  -F "3.2.0_ALLELE_MGD=@mouse_alleles.json"
```

#### Validate Data Files
```bash
POST /api/data/validate
Content-Type: multipart/form-data

curl -X POST "https://fms.alliancegenome.org/api/data/validate" \
  -F "BGI_MGD=@mouse_genes.json"
```

#### Get Release Snapshots
```bash
GET /api/data/releases
GET /api/data/releases/{version}

curl "https://fms.alliancegenome.org/api/data/releases/3.2.0"
```

#### Take Snapshot
```bash
POST /api/snapshot/take/{version}
Authorization: Bearer <token>

curl -H "Authorization: Bearer YOUR_TOKEN" \
  "https://fms.alliancegenome.org/api/snapshot/take/3.2.0"
```

### File Naming Convention

Files must follow the naming pattern:
- `[ReleaseVersion]_[DataType]_[DataSubType]`
- Example: `3.2.0_BGI_MGD`, `3.2.0_ALLELE_ZFIN`

### Response Format

Success Response:
```json
{
  "status": "success",
  "fileStatus": {
    "3.2.0_BGI_MGD": "success",
    "3.2.0_ALLELE_MGD": "success"
  }
}
```

Error Response:
```json
{
  "status": "failed",
  "fileStatus": {
    "3.2.0_BGI_MGD": "Validation error: missing required field 'symbol'"
  }
}
```

## Data Types and Submission

### Submission Process

1. **Obtain API Token**: Contact AGR administrators for an API access token
2. **Prepare Data Files**: Ensure files conform to the required JSON schemas
3. **Name Files Correctly**: Use the `ReleaseVersion_DataType_DataSubType` format
4. **Submit via API**: Use the multipart POST endpoint
5. **Check Response**: Verify all files were accepted successfully
6. **Resubmit if Needed**: Only failed files need to be resubmitted

### Validation Schemas

JSON schemas for each data type are located in:
- `/ingest/[datatype]/[datatype]MetaData.json`

Schemas define required fields, data types, and validation rules. Files are automatically validated during submission for applicable data types.

### Best Practices

- Submit related files together in a single request
- Compress large files with gzip before submission
- Use consistent release versions across submissions
- Monitor the response for validation errors
- Keep API tokens secure and rotate regularly

## Deployment

### Docker Deployment

Build and push to registry:
```bash
# Build Docker image
make docker

# Push to ECR
make registry-docker-login
make docker-push
```

### AWS Elastic Beanstalk Deployment

Initialize and deploy:
```bash
# Initialize EB application
make eb-init

# Create environment
make eb-create ENV_NAME=chipmunk-prod

# Deploy updates
make eb-deploy ENV_NAME=chipmunk-prod
```

### Production Configuration

Key production settings:
```properties
# Database
quarkus.datasource.jdbc.url=jdbc:postgresql://prod-db:5432/chipmunk
quarkus.datasource.jdbc.max-size=128

# AWS
aws.bucket.name=mod-datadumps
aws.bucket.host=https://download.alliancegenome.org

# Authentication
okta.authentication=true
okta.client.id=<client_id>
okta.url=<okta_url>
```

### Monitoring and Health Checks

- Health endpoint: `/q/health`
- Metrics endpoint: `/q/metrics`
- Application logs via CloudWatch

## Configuration

### Application Properties

The main configuration file is `src/main/resources/application.properties`. Key settings:

| Property | Description | Default |
|----------|-------------|---------|
| `quarkus.datasource.jdbc.url` | Database connection URL | localhost:5432/chipmunk |
| `aws.bucket.name` | S3 bucket for file storage | mod-datadumps-dev |
| `aws.access.key` | AWS access key | - |
| `aws.secret.key` | AWS secret key | - |
| `okta.authentication` | Enable Okta auth | false (dev) |
| `quarkus.http.limits.max-body-size` | Max upload size | unlimited |

### Environment-Specific Configuration

Use Quarkus profiles for environment-specific settings:
- `%dev.property` - Development environment
- `%test.property` - Test environment  
- `%prod.property` - Production environment

## Testing

### Unit Tests

Run unit tests:
```bash
mvn test
```

### Integration Tests

Run integration tests:
```bash
mvn verify
```

### Manual Testing

1. **Test file submission**:
```bash
# Test with sample files
curl -H "Authorization: Bearer test_token" \
  -X POST "http://localhost:8080/api/data/submit" \
  -F "TEST_BGI_MGD=@test/resources/sample_bgi.json"
```

2. **Test validation**:
```bash
curl -X POST "http://localhost:8080/api/data/validate" \
  -F "BGI_MGD=@test/resources/sample_bgi.json"
```

### Test Data

Sample test files are located in `src/test/resources/`. Use these for development and testing.

## Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Pull Request Guidelines

- Provide a clear description of changes
- Include tests for new functionality
- Update documentation as needed
- Ensure all tests pass
- Follow existing code style

### Reporting Issues

Report issues via GitHub Issues. Include:
- Description of the problem
- Steps to reproduce
- Expected vs actual behavior
- System information
- Error messages/logs

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For questions or support:
- GitHub Issues: https://github.com/alliance-genome/agr_chipmunk/issues
- Alliance Contact: admin@alliancegenome.org
- Documentation: https://www.alliancegenome.org/

## Acknowledgments

This project is maintained by the Alliance of Genome Resources, a consortium of model organism databases working together to provide unified access to genomic data.