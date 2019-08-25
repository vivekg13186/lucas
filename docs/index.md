# Welcome to Lucas

Lucas is web crawler.

## Features

* Easy to setup.
* Crawl single website.
* Scans site map.
* Auto detects urls from pages.
* Auto extract text from the web page.
* Save crawl current state support resume.
* Auto detect page updates and auto crawl the pages.

## Download

Download lucasv0.1.jar form the link [https://github.com/vivekg13186/lucas/releases/tag/0.1](https://github.com/vivekg13186/lucas/releases/tag/0.1 "https://github.com/vivekg13186/lucas/releases/tag/0.1")

##Configuration YAML 

Lucas jar file take yaml file as input.Below is the sample configuration.
```yaml
# Output directory all the json file written.
# Application need to have write permission to the directory.
# Must be full path.
# This option is mandatory.
outdir : "usr/out/path" 

# This url is starting point for crawler.
# This option is mandatory.
start : "http://www.google.com" 

# No of cralwers.
# Default value is 2.
# This option is optional.
workers : 5 

# Wait before each crawler request.
# Default value is 2 milliseconds.
# This option is optional.
delay : 3 
```
### Output 

Lucas output json files for each page crawled.Here is sample output json.

```json
{
    "url": "<< page url >>",
    "title": "<< page title >>",
    "content": "<< text content>>",
    "publishedDate": 1472029698000
}
```

### How to run

Run the jar using following command.
			
			java  -cp lucas.jar com.lucas.CLIMain ./config.yaml


### Reset state

All the crawl status stored in "lucas.db" file.Delete the file to start fresh crawling.This is the SQLLite database edit according to need,

### Run for updates

Run the crawler (make sure you have lucas.db in the folder) the crawler auto detect the changes and download only the updates

