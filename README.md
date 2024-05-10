# Attus Technical Backend Challenge

Project implemented to attend a technical challenge for Attus recruitment team.

# Schemas

## Class diagram

The following image represents the class diagram defined to attend the project's requirements.

![Class diagram showing project's class structure ](/diagrams/class-diagram.png "Class diagram for project")

## Database diagram

The following image represents the table structure defined to attend the project's requirements.

![Database diagram showing database table structure ](/diagrams/database-diagram.png "Database diagram for project")

# API

## PersonAPI

### GET /person/all

Retrieves every person and their addresses in the system.

### GET /person/:id

Retriever a person and it's addresses given a PersonIdentifier as Long.

### POST /person

Create a person.

```
{
  "firstName": String required,
  "lastName": String required,
  "birthDate": String required format(dd-MM-yyyy)
}
```

### PUT /person/:id

Updates fields from a person given a person id.

```
{
  "firstName": String optional,
  "lastName": String optional,
  "birthDate": String optional format(dd-MM-yyyy)
}
```

### DELETE /person/:id

Removes a person given a person id.

## Address API

### GET /person/:personId/address

Retrieves all address for person given a person id.

### POST /person/:personId/address

Creates an address for a person given the person id. The first address will be enforced as main address.

```
{
  "postalCode": String required format(XXXX-XXX) where X is a digit,
  "state": String required format(this value must be a title case state name or a uppercase state acronym eg. "São Paulo" or "SP"),
  "city": String required format(No digits or punctuation allowed),
  "street": String required,
  "number": String required
}
```

### PUT /address/:id

Updates a field given an address id.

```
{
  "postalCode": String optional format(XXXX-XXX) where X is a digit,
  "state": String optional format(this value must be a title case state name or a uppercase state acronym eg. "São Paulo" or "SP"),
  "city": String optional format(No digits or punctuation allowed),
  "street": String optional,
  "number": String optional
}
```

### DELETE /address/:addressId

Deletes an address if not main address for person.

### POST /:personId/address/:addressId

Updates a person's main address.

```
  empty body
```

# Code coverage

Code coverage was measured using IntelliJ built-in Jacoco coverage plugin.
