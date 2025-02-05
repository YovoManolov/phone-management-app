# Liquibase Database Setup for the Phone Management App

This folder contains the Liquibase changelogs for setting up the database schema for the **Phone Management App**.

## Folder Structure

- **changelog-master.yaml**: The main changelog file that includes all other changelogs.
- **schemas/**: Contains individual schema definitions (e.g., `customers.yaml`, `subscriptions.yaml`).
- **tables/**: Contains definitions for tables in different schemas (e.g., `customer-table.yaml`, `subscription-table.yaml`).
- **constraints/**: Contains foreign key and other constraint definitions (e.g., `foreign-keys.yaml`).

## How to Run Liquibase

### 1. **Ensure Database is Running**
Make sure your database is up and running. If you're using Docker, use the following command to start PostgreSQL:


```bash
docker run -d -p 5432:5432 --name phone_management_db postgres:latest
