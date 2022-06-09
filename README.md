## API Hex Translation

## Assumptions

* As per the examples, byte position is left to right.
* The keys are 7 characters wide (i.e. 28 bits) - should they be 8 (32 bits)?  (Assuming 7 as there are 3 and all are.)

### Goal

Using Java, create a REST based endpoint that will accept a JSON payload being POST with hexadecimal encoded key/value pairs and convert the content into a different JSON structure that is more developer friendly using the provided data dictionary.

### Technology assumptions

 - API implementation must be performed in Java
 - Libraries may be used to assist with the implementation
 - Provided data dictionary specifies how to intrepret the hex data

### Task

A JSON payload with a `key` and `value` parameter is passed as an HTTP POST payload to an endpoint.

The implementation shall take that input and use the provided data dictionary below.

JSON payload shall be returned representing the decoded data in an easier to read form based on the data dictionary.

**In Scope:**

 - Parsing and basic input validation on input JSON payload
 - Parsing data using data Dictionary (support to parse all 3 keys each returning different response payload)
 - Generating output JSON payload

**Out of Scope:**

 - Support additional data types not specified in data dictionary
 - Support parsing additional keys not provided

**Example Request**

```
curl --location --request POST 'http://localhost:8080/parse' \
--header 'Content-Type: application/json' \
--data-raw '{"key": "0000001", "value": "01"}'
```

**Example Response**

```
{"door_status": "open"}
```

## Data Dictionary


**Key: 0000001**

**Name:** Door Status

**Length:** 1 byte

| Byte Position | Bit Position | Name | Type | Details |
|---------------|--------------|------|------|---------|
| 0             | -            | Door status | Enum / UInt8 | 0: Closed<br/>1: Open |

**Example:** An open door would report a value of "01"

**Key: 0000002**

**Name:** Cooking Notifications

**Length:** 1 byte

| Byte Position | Bit Position | Name | Type | Details |
|---------------|--------------|------|------|---------|
| 0             | 0            | Target Temperature Achieved | bit | |
| 0             | 1            | Cooking Started | bit | |
| 0             | 2            | Cooktime One Minute Remaining | bit | |
| 0             | 3            | Cooking Complete | bit | |
| 0             | 4-7          | n/a  | unused | |

**Example:** A cooking started notification would report "02"


**Key: 0000004**

**Name:** Current Cooking Parameters

**Length:** 11 bytes


| Byte Position | Bit Position | Name | Type | Details |
|---------------|--------------|------|------|---------|
| 0             | -            | Shade | UInt8  | |
| 1             | -            | Size | Enum/UInt8 | 0: Small<br/>1: Medium<br/>2: Large |
| 2-3           | -            | Temperature (F) | UInt16 | |
| 4-7           | -            | Cook Time (Seconds) | UInt32 | |
| 8             | -            | Count | UInt8 | |
| 9             | -            | Cook Mode | Enum/UInt8 | 0: Air Fry<br/>1: Bake<br/>2: Broil<br/>3: Roast<br/>4: Reheat<br/>5: Warm<br/>6: Slow Cook<br/>7: Dehydrate<br/>8: Proof<br/>9: Cookie<br/>10: Pizza<br/>11: Bagel<br/>12: Toast<br/>13: Crisp Finish<br/>14: Cake<br/>15: Cookie with Preference<br/>16: Pizza with Preference  |
| 10            | -            | Preferences | UInt8 | |
| 11            | -            | n/a | unused | |

**Example:** Air Fry a small size at 400 degrees for 1200 seconds would report "00000190000004B000000000"


## Guidelines

 - Focus is on decoding the binary data to JSON, not the REST endpoint

## Submission

 - Submit complete program via github.com repo (or zip file) back to the interviewer
   - Email program 24 hours prior to follow-up interview
 - Solution is expected to take ~1-2 hours to implement
 - Demonstrate solution in follow-up interview
 - Remember, the key word is 'Idempotence'