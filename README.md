# ZapisovateleCBA
Season stats for CBA baseball scorers (v2022)

## Description and usage

This Java program fetches https://www.baseball.cz/zapisovatele/nominace/ via HTTPClient and then parses the contents. It looks for number of games assigned to each scorer (10th column) in different leagues (1st column). Based on the number of games for each scorer his reward is being counted. The results are finally printed out to the standard output.

## Possible future development

* Configurable rules for rewards (currently hardcoded)
* Log results into files
* Export into XLSX