# INSTALLATION
## MINIKUBE
brew install minikube
minikube start
minikube delete --all
minikube addons enable metrics-server

## PROMETHEUS
kubectl create namespace monitoring
helm install prometheus prometheus-community/prometheus --namespace monitoring
export PROMETHEUS_POD_NAME	=$(kubectl get pods --namespace monitoring -l "app.kubernetes.io/name=prometheus,app.kubernetes.io/instance=prometheus" -o jsonpath="{.items[0].metadata.name}")
kubectl --namespace monitoring port-forward $PROMETHEUS_POD_NAME 9091

#GRAFANA
helm repo add grafana https://grafana.github.io/helm-charts
helm search repo grafana
helm install grafana grafana/grafana --namespace monitoring
kubectl get secret --namespace monitoring grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo
export GRAFANA_POD_NAME=$(kubectl get pods --namespace monitoring -l "app.kubernetes.io/name=grafana,app.kubernetes.io/instance=grafana" -o jsonpath="{.items[0].metadata.name}")
kubectl --namespace monitoring port-forward $GRAFANA_POD_NAME 3000

## POSTGRES - STAGE
kubectl create namespace develop
kubectl apply -f develop.postgres-config.yaml
kubectl apply -f develop.postgres-pv.yaml
kubectl apply -f develop.postgres-sv.yaml
kubectl apply -f develop.postgres.yaml

## PRINTER3D BACKEND
kubectl apply -f deployment-backend.yaml
	