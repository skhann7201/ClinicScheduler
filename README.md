# RU Clinic Scheduler

## Overview
This project enhances the RU Clinic scheduling system by adding support for office and imaging appointments, managing a rotating list of technicians for imaging services, and validating appointment data. The system builds upon the scheduler from Project 1 and includes new features such as handling new appointment types, provider credit reporting, and improved error checking.

## Features

- **Appointment Management**: Schedule, cancel, or reschedule office and imaging appointments.
- **Circular Technician Rotation**: Technicians are assigned to imaging appointments in a rotating order.
- **Data Validation**: Ensures valid dates, timeslots, and patient/provider data.
- **Provider Credit Reporting**: Displays expected credits for providers based on the services provided.
- **Command-Based Interface**: Use commands to manage appointments (e.g., `D`, `T`, `C`, `R`).

## Usage
1. **Compile** the Java classes.
2. **Run** `ClinicManager.java` to start the terminal interface.
3. **Enter commands** for appointment scheduling and management.

### Example Commands
- `D,9/30/2024,1,John,Doe,12/13/1989,120` - Schedule office appointment.
- `T,9/30/2024,1,John,Doe,12/13/1989,xray` - Schedule imaging appointment.
- `C,9/30/2024,1,John,Doe,12/13/1989` - Cancel appointment.
- `PO`, `PI`, `PC` - Display sorted appointment and credit information.

## JUnit Tests
Includes tests for:
- **Date validation** (`DateTest`)
- **Profile comparison** (`ProfileTest`)
- **List operations** (`ListTest`)
