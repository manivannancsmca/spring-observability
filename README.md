# 🚀 Step-by-Step Execution Guide

## Step 1: Start the Observability Infrastructure

In the terminal directory containing `docker-compose.yml` and `tempo.yaml`, run:

```bash
docker-compose up -d
```

Verify that all containers are healthy and running:

```bash
docker ps
```

---

## Step 2: Build and Run Spring Boot Applications

### Start Order Service

Open a terminal in the `order-service` directory:

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

### Start Payment Service

Open a second terminal in the `payment-service` directory:

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

---

## Step 3: Generate Test Traces

Traces are only sent to Tempo when API endpoints are called.

Make 3–5 requests to your endpoints.

### Call Order Service

```bash
curl -X GET http://localhost:8081/orders
```

### Call Payment Service

```bash
curl -X GET http://localhost:8082/payments
```

---

## 🔍 Step 4: Verify Registration in Tempo

### Option A: Check Registered Services via Browser API

Open the following URL in your web browser:

```text
http://localhost:3200/api/v2/search/tag/resource.service.name/values
```

### Expected Result

```json
["order-service", "payment-service"]
```

### Option B: Check Raw Service Tags Endpoint

Open:

```text
http://localhost:3200/api/v2/search/tags
```

Verify that `"name": "resource"` contains:

```text
service.name
```

---

## 📊 Step 5: Configure Grafana to View Traces

Open Grafana in your browser:

```text
http://localhost:3000
```

### Grafana Login

| Setting | Value |
|---|---|
| Username | `admin` |
| Password | `admin` |

### Add Tempo Data Source

1. Go to **Connections > Data Sources**.
2. Click **Add data source**.
3. Select **Tempo**.
4. Set the URL to:

```text
http://tempo:3200
```

5. Expand **Node Graph** settings.
6. Toggle **Enable Node Graph** to **On**.
7. Click **Save & Test**.
8. You should see a green success banner.

---

## 🔎 Search and Query Traces in Grafana

1. Navigate to **Explore** using the compass icon on the left toolbar.
2. Select **Tempo** from the top-left data source dropdown.
3. Switch the query mode to **TraceQL**.
4. Enter the following query:

```traceql
{ resource.service.name = "order-service" }
```

5. Click **Run Query**.

Grafana should display the matching traces along with the trace timeline visualizer.

---

## ✅ Verification Checklist

- [ ] Docker Compose infrastructure is running.
- [ ] Tempo container is healthy.
- [ ] Order Service is running on port `8081`.
- [ ] Payment Service is running on port `8082`.
- [ ] Test API requests have been executed.
- [ ] `order-service` appears in Tempo service names.
- [ ] `payment-service` appears in Tempo service names.
- [ ] Grafana is accessible on port `3000`.
- [ ] Tempo is configured as a Grafana data source.
- [ ] Node Graph is enabled.
- [ ] TraceQL query returns `order-service` traces.
