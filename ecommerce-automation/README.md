# Demoblaze E2E Automation Framework (Selenium + TestNG + POM)

### How to run
```bash
# 1) Import as Maven project
# 2) Run with all 3 browsers in parallel:
mvn clean test
# Or single browser:
mvn clean test -Dbrowser=chrome
```

- Reports: `reports/ExtentReport.html`
- Logs: `reports/framework.log`
- Screenshots: `reports/screenshots/`

Credentials for tests are read from `config.properties`.
